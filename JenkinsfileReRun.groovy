pipeline {
    agent {
        label 'Windows||Linux'
    }
    triggers {
        upstream(upstreamProjects: 'parent_project', threshold: hudson.model.Result.UNSTABLE)
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
        stage('SelectTest') {
            steps {
                script {
                    def command = 'mvn --projects tests -Dexec.classpathScope="test" exec:java -Dexec.mainClass="test.rerun.ReRun" -Dexec.cleanupDaemonThreads=false';
                    if (isUnix()) {
                        sh command
                    } else {
                        bat command
                    }
                }
            }
        }
        stage('RunSelectTest') {
            environment {
                ALLURE_TESTPLAN_PATH = "target/testplan.json"
            }
            steps {
                // https://docs.qameta.io/allure-testops/upload/jenkins/
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    withAllureUpload(serverId: 'allure-testops', projectId: '1', results: [[path: 'tests/target/allure-results']]) {
                        script {
                            def command = "mvn test --projects tests -Dtest_browser=${params.test_browser} -Dtest_display_visible=false -Dtest_display_size=${params.test_display_size} -DenvironmentName=${params.environmentName}";
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
            // https://www.jenkins.io/doc/pipeline/steps/junit/
            junit 'tests/target/surefire-reports/TEST-*.xml'
        }
    }
}