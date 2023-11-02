/*
 * Copyright (c) 2016 JFoenix
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.jfoenix.skins;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.transitions.CachedTransition;
import com.jfoenix.utils.JFXNodeUtils;
import com.sun.javafx.scene.control.skin.ButtonSkin;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * <h1>Material Design Button Skin</h1>
 *
 * @author Shadi Shaheen
 * @version 1.0
 * @since 2016-03-09
 */
public class JFXButtonSkin extends ButtonSkin {

    private Transition clickedAnimation;
    private JFXRippler buttonRippler;
    private Runnable releaseManualRippler = null;
    private boolean invalid = true;
    private boolean mousePressed = false;

    public JFXButtonSkin(JFXButton button) {
        super(button);

        buttonRippler = new JFXRippler(getSkinnable()) {
            @Override
            protected Node getMask() {
                StackPane mask = new StackPane();
                mask.shapeProperty().bind(getSkinnable().shapeProperty());
                JFXNodeUtils.updateBackground(getSkinnable().getBackground(), mask);
                mask.resize(getWidth() - snappedRightInset() - snappedLeftInset(),
                    getHeight() - snappedBottomInset() - snappedTopInset());
                return mask;
            }

            @Override
            protected void positionControl(Node control) {
                // do nothing as the controls is not inside the ripple
            }
        };

        // add listeners to the button and bind properties
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> playClickAnimation(1));
//        button.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> playClickAnimation(-1));
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> mousePressed = true);
        button.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> mousePressed = false);
        button.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> mousePressed = false);

        button.ripplerFillProperty().addListener((o, oldVal, newVal) -> buttonRippler.setRipplerFill(newVal));

        button.armedProperty().addListener((o, oldVal, newVal) -> {
            if (newVal) {
                if (!mousePressed) {
                    releaseManualRippler = buttonRippler.createManualRipple();
                    playClickAnimation(1);
                }
            } else {
                if (releaseManualRippler != null) {
                    releaseManualRippler.run();
                    releaseManualRippler = null;
                }
                playClickAnimation(-1);
            }
        });

        // show focused state
        button.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!button.disableVisualFocusProperty().get()) {
                if (newVal) {
                    if (!getSkinnable().isPressed()) {
                        buttonRippler.setOverlayVisible(true);
                    }
                } else {
                    buttonRippler.setOverlayVisible(false);
                }
            }
        });

        button.buttonTypeProperty().addListener((o, oldVal, newVal) -> updateButtonType(newVal));

        updateButtonType(button.getButtonType());

        updateChildren();
    }

    @Override
    protected void updateChildren() {
        super.updateChildren();
        if (buttonRippler != null) {
            getChildren().add(0, buttonRippler);
        }
        for (int i = 1; i < getChildren().size(); i++) {
            final Node child = getChildren().get(i);
            if (child instanceof Text) {
                child.setMouseTransparent(true);
            }
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h) {
        if (invalid) {
            if (((JFXButton) getSkinnable()).getRipplerFill() == null) {
                // change rippler fill according to the last LabeledText/Label child
                for (int i = getChildren().size() - 1; i >= 1; i--) {
                    if (getChildren().get(i) instanceof LabeledText) {
                        buttonRippler.setRipplerFill(((LabeledText) getChildren().get(i)).getFill());
                        ((LabeledText) getChildren().get(i)).fillProperty()
                            .addListener((o, oldVal, newVal) -> buttonRippler.setRipplerFill(
                                newVal));
                        break;
                    } else if (getChildren().get(i) instanceof Label) {
                        buttonRippler.setRipplerFill(((Label) getChildren().get(i)).getTextFill());
                        ((Label) getChildren().get(i)).textFillProperty()
                            .addListener((o, oldVal, newVal) -> buttonRippler.setRipplerFill(
                                newVal));
                        break;
                    }
                }
            } else {
                buttonRippler.setRipplerFill(((JFXButton) getSkinnable()).getRipplerFill());
            }
            invalid = false;
        }
        buttonRippler.resizeRelocate(
            getSkinnable().getLayoutBounds().getMinX(),
            getSkinnable().getLayoutBounds().getMinY(),
            getSkinnable().getWidth(), getSkinnable().getHeight());
        layoutLabelInArea(x, y, w, h);
    }


    private void updateButtonType(ButtonType type) {
        switch (type) {
            case RAISED:
                JFXDepthManager.setDepth(getSkinnable(), 2);
                clickedAnimation = new ButtonClickTransition(getSkinnable(), (DropShadow) getSkinnable().getEffect());
                /*
                 * disable action when clicking on the button shadow
                 */
                getSkinnable().setPickOnBounds(false);
                break;
            default:
                getSkinnable().setEffect(null);
                getSkinnable().setPickOnBounds(true);
                break;
        }
    }

    private void playClickAnimation(double rate) {
        if (clickedAnimation != null) {
            if (!clickedAnimation.getCurrentTime().equals(clickedAnimation.getCycleDuration()) || rate != 1) {
                clickedAnimation.setRate(rate);
                clickedAnimation.play();
            }
        }
    }

    private static class ButtonClickTransition extends CachedTransition {
        ButtonClickTransition(Node node, DropShadow shadowEffect) {
            super(node, new Timeline(
                    new KeyFrame(Duration.ZERO,
                        new KeyValue(shadowEffect.radiusProperty(),
                            JFXDepthManager.getShadowAt(2).radiusProperty().get(),
                            Interpolator.EASE_BOTH),
                        new KeyValue(shadowEffect.spreadProperty(),
                            JFXDepthManager.getShadowAt(2).spreadProperty().get(),
                            Interpolator.EASE_BOTH),
                        new KeyValue(shadowEffect.offsetXProperty(),
                            JFXDepthManager.getShadowAt(2).offsetXProperty().get(),
                            Interpolator.EASE_BOTH),
                        new KeyValue(shadowEffect.offsetYProperty(),
                            JFXDepthManager.getShadowAt(2).offsetYProperty().get(),
                            Interpolator.EASE_BOTH)
                    ),
                    new KeyFrame(Duration.millis(1000),
                        new KeyValue(shadowEffect.radiusProperty(),
                            JFXDepthManager.getShadowAt(5).radiusProperty().get(),
                            Interpolator.EASE_BOTH),
                        new KeyValue(shadowEffect.spreadProperty(),
                            JFXDepthManager.getShadowAt(5).spreadProperty().get(),
                            Interpolator.EASE_BOTH),
                        new KeyValue(shadowEffect.offsetXProperty(),
                            JFXDepthManager.getShadowAt(5).offsetXProperty().get(),
                            Interpolator.EASE_BOTH),
                        new KeyValue(shadowEffect.offsetYProperty(),
                            JFXDepthManager.getShadowAt(5).offsetYProperty().get(),
                            Interpolator.EASE_BOTH)
                    )
                )
            );
            // reduce the number to increase the shifting , increase number to reduce shifting
            setCycleDuration(Duration.seconds(0.2));
            setDelay(Duration.seconds(0));
        }
    }
}
