package polyakova.test.log;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logging of methods marked with an annotation "Test"
 *
 * @author Iuliia Poliakova
 */
public class Tests2LogListener implements TestExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(Tests2LogListener.class);

    public void executionStarted(TestIdentifier testIdentifier) {
        log.info("Begin {}", testIdentifier.getDisplayName());
    }

    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        log.info("End {}", testIdentifier.getDisplayName());
    }
}
