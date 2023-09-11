pipeline {
    agent {
        label 'Windows||Linux'
    }
    // https://www.jenkins.io/doc/book/pipeline/syntax/#triggers
    triggers {
        cron('0 19 * * *')
    }
    options {
        disableConcurrentBuilds()
        timeout(time: 24, unit: 'HOURS')
    }
    // https://www.jenkins.io/doc/book/pipeline/syntax/#parameters
    parameters {
        choice(name: 'environmentName', choices: ['environment_local', 'environment_dev', 'environment_release'], description: 'File name with environment properties')
        choice(name: 'test_browser', choices: ['CHROME', 'EDGE', 'OPERA', 'IE', 'FIREFOX'], description: 'Browser name')
        choice(name: 'test_display_size', choices: ['FHD', 'VGA', 'SVGA', 'XGA', 'WXGA_H', 'WXGA', 'WSXGA', 'WUXGA'], description: 'Browser windows size')
    }
    stages {
        stage('Clean') {
            steps {
                script {
                    def command = "mvn clean";
                    if (isUnix()) {
                        sh command
                    } else {
                        bat command
                    }
                }
            }
        }
        stage('Install') {
            steps {
                script {
                    def command = "mvn install -DskipTests";
                    if (isUnix()) {
                        sh command
                    } else {
                        bat command
                    }
                }
            }
        }
        stage('Web+Mobile') {
            parallel {
                stage('Web') {
                    steps {
                        // https://docs.qameta.io/allure-testops/upload/jenkins/
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            withAllureUpload(serverId: 'allure-testops', projectId: '1', results: [[path: 'tests/target/allure-results']], name: 'Jenkins-web - #${BUILD_NUMBER}') {
                                script {
                                    def command = "mvn test --projects tests -Dtest_browser=${params.test_browser} -Dtest_display_size=${params.test_display_size} -DenvironmentName=${params.environmentName}";
                                    if (isUnix()) {
                                        sh command
                                    } else {
                                        bat command
                                    }
                                }
                            }
                        }
                    }
                }
                stage('Mobile') {
                    steps {
                        // https://docs.qameta.io/allure-testops/upload/jenkins/
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            withAllureUpload(serverId: 'allure-testops', projectId: '1', results: [[path: 'tests-mobile/target/allure-results']], name: 'Jenkins-mobile - #${BUILD_NUMBER}') {
                                script {
                                    def command = "mvn test --projects tests-mobile -Dtest_browser=${params.test_browser} -Dtest_display_size=${params.test_display_size} -DenvironmentName=${params.environmentName}";
                                    if (isUnix()) {
                                        sh command
                                    } else {
                                        bat command
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        stage('Integration') {
            steps {
                // https://docs.qameta.io/allure-testops/upload/jenkins/
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    withAllureUpload(serverId: 'allure-testops', projectId: '1', results: [[path: 'tests-integration/target/allure-results']], name: 'Jenkins-integration - #${BUILD_NUMBER}') {
                        script {
                            def command = "mvn test --projects tests-integration -Dtest_browser=${params.test_browser} -Dtest_display_size=${params.test_display_size} -DenvironmentName=${params.environmentName}";
                            if (isUnix()) {
                                sh command
                            } else {
                                bat command
                            }
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: 'tests/target/allure-results/*.json'
            archiveArtifacts artifacts: 'tests-mobile/target/allure-results/*.json'
            archiveArtifacts artifacts: 'tests-integration/target/allure-results/*.json'
            // https://www.jenkins.io/doc/pipeline/steps/junit/
            junit '**/target/surefire-reports/TEST-*.xml'
        }
    }
}