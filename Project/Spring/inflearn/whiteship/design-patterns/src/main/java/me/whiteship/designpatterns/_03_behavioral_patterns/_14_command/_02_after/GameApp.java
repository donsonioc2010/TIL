package me.whiteship.designpatterns._03_behavioral_patterns._14_command._02_after;

import me.whiteship.designpatterns._03_behavioral_patterns._14_command._01_before.Game;

public class GameApp {

    private Command command;

    public GameApp(Command command) {
        this.command = command;
    }

    public void press() {
        command.execute();
    }
    public void undo() {command.undo();}

    public static void main(String[] args) {
        GameApp gameApp = new GameApp(new GameStartCommand(new Game()));
        gameApp.press();
        gameApp.undo();
    }
}
