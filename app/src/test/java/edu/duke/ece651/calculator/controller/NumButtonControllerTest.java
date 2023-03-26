package edu.duke.ece651.calculator.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.util.WaitForAsyncUtils;

import edu.duke.ece651.calculator.model.RPNStack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


@ExtendWith(ApplicationExtension.class)
public class NumButtonControllerTest {
  private TextField testText;
  private NumButtonController cont;
  private RPNStack model;
  
  //get called before each test.
  @Start
  private void start(Stage stage){
    testText = new TextField();
    model = mock(RPNStack.class);
    cont = new NumButtonController(model);
    cont.currentNumber = testText;
  }


  private void addNums(String ... strs) {
    Platform.runLater(()->{
        for (String s: strs){
          Button b = new Button(s);
          cont.onNumberButton(new ActionEvent(b,null));
} });
    WaitForAsyncUtils.waitForFxEvents();
  }
  
  @Test
  public void test_onNumberButton_7(FxRobot robot) {
    addNums("7");
    FxAssert.verifyThat(testText, TextInputControlMatchers.hasText("7"));
  }

  @Test
  public void test_onNumberButton_pi(FxRobot robot){
    addNums("3",".","1","4");
    FxAssert.verifyThat(testText, TextInputControlMatchers.hasText("3.14"));
  }


  private void enterNum(String text){
    Platform.runLater(()->{
        testText.setText(text);
        Button b = new Button("Enter");
        cont.onEnter(new ActionEvent(b,null));
      });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  void test_enterButton_num(FxRobot robot) {
    enterNum("1234.5");
    verify(model).pushNum(1234.5);
    verifyNoMoreInteractions(model);
    FxAssert.verifyThat(testText, TextInputControlMatchers.hasText(""));
  }

  @Test
  void test_enterButton_whitespace(FxRobot robot) {
    enterNum("    ");
    verifyNoMoreInteractions(model);
    FxAssert.verifyThat(testText, TextInputControlMatchers.hasText(""));
  }


  @Test
  void test_onPlus(){
    Platform.runLater(()->{
        testText.setText("3.14");
        Button b = new Button("+");
        cont.onPlus(new ActionEvent(b,null));
      });
    WaitForAsyncUtils.waitForFxEvents();
    verify(model).pushNum(3.14);
    verify(model).add();
    verifyNoMoreInteractions(model);
    FxAssert.verifyThat(testText, TextInputControlMatchers.hasText(""));
  }

   @Test
  void test_onMinus(){
    Platform.runLater(()->{
        testText.setText("3.14");
        Button b = new Button("-");
        cont.onMinus(new ActionEvent(b,null));
      });
    WaitForAsyncUtils.waitForFxEvents();
    verify(model).pushNum(3.14);
    verify(model).subtract();
    verifyNoMoreInteractions(model);
    FxAssert.verifyThat(testText, TextInputControlMatchers.hasText(""));
  }

   @Test
  void test_onTimes(){
    Platform.runLater(()->{
        testText.setText("3.14");
        Button b = new Button("*");
        cont.onTimes(new ActionEvent(b,null));
      });
    WaitForAsyncUtils.waitForFxEvents();
    verify(model).pushNum(3.14);
    verify(model).times();
    verifyNoMoreInteractions(model);
    FxAssert.verifyThat(testText, TextInputControlMatchers.hasText(""));
  }

   @Test
  void test_onDivide(){
    Platform.runLater(()->{
        testText.setText("3.14");
        Button b = new Button("/");
        cont.onDivide(new ActionEvent(b,null));
      });
    WaitForAsyncUtils.waitForFxEvents();
    verify(model).pushNum(3.14);
    verify(model).divide();
    verifyNoMoreInteractions(model);
    FxAssert.verifyThat(testText, TextInputControlMatchers.hasText(""));
  }

  
}
