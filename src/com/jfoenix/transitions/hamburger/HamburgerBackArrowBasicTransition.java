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

package com.jfoenix.transitions.hamburger;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.CachedTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 * transform {@link JFXHamburger} into left arrow
 *
 * @author Shadi Shaheen
 * @version 1.0
 * @since 2016-03-09
 */
public class HamburgerBackArrowBasicTransition extends CachedTransition implements HamburgerTransition {

    public HamburgerBackArrowBasicTransition() {
        super(null, null);
    }

    public HamburgerBackArrowBasicTransition(JFXHamburger burger) {
        super(burger, createTimeline(burger));
        timeline.bind(Bindings.createObjectBinding(() -> createTimeline(burger),
            burger.widthProperty(),
            burger.heightProperty(),
            ((Region) burger.getChildren().get(0)).widthProperty(),
            ((Region) burger.getChildren().get(0)).heightProperty()));
        // reduce the number to increase the shifting , increase number to reduce shifting
        setCycleDuration(Duration.seconds(0.3));
        setDelay(Duration.seconds(0));
    }

    private static Timeline createTimeline(JFXHamburger burger) {
        double burgerWidth = burger.getChildren().get(0).getLayoutBounds().getWidth();
        double burgerHeight = burger.getChildren().get(2).getBoundsInParent().getMaxY() - burger.getChildren()
            .get(0)
            .getBoundsInParent()
            .getMinY();

        double hypotenuse = Math.sqrt(Math.pow(burgerHeight / 2 - burger.getChildren()
                                                                      .get(0)
                                                                      .getLayoutBounds()
                                                                      .getHeight() / 2, 2) + Math.pow(burgerWidth / 2,
            2));
        double angle = Math.toDegrees(Math.asin((burgerHeight / 2 - burger.getChildren()
                                                                        .get(0)
                                                                        .getLayoutBounds()
                                                                        .getHeight() / 2) / hypotenuse));

        double burgerDiagonal = Math.sqrt(Math.pow(burger.getChildren().get(0).getLayoutBounds().getHeight(),
            2) + Math.pow(burger.getChildren()
                              .get(0)
                              .getBoundsInParent()
                              .getWidth() / 2, 2));
        double theta = (90 - angle) + Math.toDegrees(Math.atan((burger.getChildren()
                                                                    .get(0)
                                                                    .getLayoutBounds()
                                                                    .getHeight()) / (burger.getChildren()
                                                                                         .get(0)
                                                                                         .getBoundsInParent()
                                                                                         .getWidth() / 2)));
        double hOffset = Math.cos(Math.toRadians(theta)) * burgerDiagonal / 2;
        double transY = burger.getChildren().get(0).getLayoutBounds().getHeight() / 2 + burger.getSpacing() - hOffset;
        double transX = burgerWidth / 2 - Math.sin(Math.toRadians(theta)) * (burgerDiagonal / 2);

        return new Timeline(
            new KeyFrame(
                Duration.ZERO,
                new KeyValue(burger.rotateProperty(), 0, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(0).rotateProperty(), 0, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(0).translateYProperty(), 0, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(0).translateXProperty(), 0, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(0).scaleXProperty(), 1, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(2).rotateProperty(), 0, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(2).translateYProperty(), 0, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(2).translateXProperty(), 0, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(2).scaleXProperty(), 1, Interpolator.EASE_BOTH)

            ),
            new KeyFrame(Duration.millis(1000),
                new KeyValue(burger.rotateProperty(), 0, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(0).rotateProperty(), -angle, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(0).translateYProperty(), transY, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(0).translateXProperty(), -transX, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(0).scaleXProperty(), 0.5, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(2).rotateProperty(), angle, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(2).translateYProperty(), -transY, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(2).translateXProperty(), -transX, Interpolator.EASE_BOTH),
                new KeyValue(burger.getChildren().get(2).scaleXProperty(), 0.5, Interpolator.EASE_BOTH)
            )
        );
    }

    public Transition getAnimation(JFXHamburger burger) {
        return new HamburgerBackArrowBasicTransition(burger);
    }

}
