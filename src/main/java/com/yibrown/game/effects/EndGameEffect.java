package com.yibrown.game.effects;

import com.yibrown.game.Game;

public class EndGameEffect extends Effect {

    private Game game;

    public EndGameEffect(Game game) {
        this.game = game;
    }

    public void applyEffect(boolean doesHelp) {
        game.endGame();
    }
}
