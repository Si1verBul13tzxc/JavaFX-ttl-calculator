package edu.duke.ece651.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

@ExtendWith(ApplicationExtension.class)
public class ErrorReporterTest {
  @Test
  public void test_alert(FxRobot robot) {
    ErrorReporter er = new ErrorReporter();
    Platform.runLater(()->er.uncaughtException(Thread.currentThread(),
                                               new IllegalStateException("Test exception")));
    WaitForAsyncUtils.waitForFxEvents();
    DialogPane errorDialog = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
    assertEquals("java.lang.IllegalStateException", errorDialog.getHeaderText());
    assertEquals("Test exception", errorDialog.getContentText());
    Node ok = errorDialog.lookupButton(ButtonType.OK);
    assertNotNull(ok);
    robot.clickOn(ok);
  }

}
