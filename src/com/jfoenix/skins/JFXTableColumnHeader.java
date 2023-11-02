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

import com.sun.javafx.scene.control.skin.TableColumnHeader;
import com.sun.javafx.scene.control.skin.TableViewSkinBase;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumnBase;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * @author Shadi Shaheen
 */
public class JFXTableColumnHeader extends TableColumnHeader {

//    private StackPane container = new StackPane();
//    private StackPane arrowContainer = new StackPane();
//    private GridPane arrowPane;
//    private Region arrow;
//    private Timeline arrowAnimation;
//    private double currentArrowRotation = -1;
//    private boolean invalid = true;
//    private Insets oldMargin = null;

    public JFXTableColumnHeader(TableViewSkinBase skin, TableColumnBase tc) {
        super(skin, tc);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
//        double w = snapSize(getWidth()) - (snappedLeftInset() + snappedRightInset());
//        container.setStyle("-fx-border-color: RED");
//        container.resizeRelocate(snappedLeftInset(), 0, w, getHeight());
//        if (!getChildren().contains(container)) {
//            invalid = true;
//            container.getChildren().remove(arrowContainer);
//            for (int i = 0; i < getChildren().size(); ) {
//                Node child = getChildren().get(i);
//                container.getChildren().add(child);
//            }
//            getChildren().add(container);
//        }
//
//        // add animation to sorting arrow
//        if (invalid) {
//            if (container.getChildren().size() > 1 && !container.getChildren().contains(arrowContainer)) {
//                // setup children
//                arrowPane = (GridPane) container.getChildren().get(1);
//                arrow = (Region) arrowPane.getChildren().get(0);
//                arrowContainer.getChildren().clear();
//                container.getChildren().remove(1);
//                container.getChildren().add(arrowContainer);
//
//                for (int i = 0; i < arrowPane.getChildren().size(); ) {
//                    Node child = arrowPane.getChildren().get(i);
//                    arrowContainer.getChildren().add(child);
//                    if (child instanceof HBox) {
//                        HBox dotsContainer = (HBox) child;
//                        dotsContainer.setMaxHeight(5);
//                        dotsContainer.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
//                            return arrow.getHeight() + 2;
//                        }, arrow.heightProperty()));
//                    } else if (child instanceof Label) {
//                        Label labelContainer = (Label) child;
//                        labelContainer.setMaxHeight(5);
//                        labelContainer.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
//                            return arrow.getHeight() + 3;
//                        }, arrow.heightProperty()));
//                    }
//                }
//
//                arrowContainer.maxWidthProperty().bind(arrow.widthProperty());
//                StackPane.setAlignment(arrowContainer, Pos.CENTER_RIGHT);
//
//
//                // set padding to the label to replace it with ... if it's too close to the sorting arrow
//                Label label = (Label) container.getChildren().get(0);
//                oldMargin = StackPane.getMargin(label);
//                StackPane.setMargin(label,
//                    new Insets(oldMargin == null ? 0 : oldMargin.getTop(),
//                        oldMargin == null || oldMargin.getRight() < 30 ? 30 : oldMargin.getRight(),
//                        oldMargin == null ? 0 : oldMargin.getBottom(),
//                        oldMargin == null || oldMargin.getLeft() < 30 ? 30 : oldMargin.getLeft()));
//
//                // fixed the issue of arrow translate X while resizing the column header
//                arrowContainer.translateXProperty().bind(Bindings.createDoubleBinding(() -> {
//                    if (arrowContainer.getLayoutX() <= 8) {
//                        return -arrowContainer.getLayoutX() - 2;
//                    }
//                    return -10.0;
//                }, arrowContainer.layoutXProperty()));
//
//
//                if (arrowAnimation != null && arrowAnimation.getStatus() == Status.RUNNING) {
//                    arrowAnimation.stop();
//                }
//                if (arrow.getRotate() == 180 && arrow.getRotate() != currentArrowRotation) {
//                    arrowContainer.setOpacity(0);
//                    arrowContainer.setTranslateY(getHeight() / 4);
//                    arrowAnimation = new Timeline(new KeyFrame(Duration.millis(320),
//                        new KeyValue(arrowContainer.opacityProperty(),
//                            1,
//                            Interpolator.EASE_BOTH),
//                        new KeyValue(arrowContainer.translateYProperty(),
//                            0,
//                            Interpolator.EASE_BOTH)));
//                } else if (arrow.getRotate() == 0 && arrow.getRotate() != currentArrowRotation) {
//                    arrow.setRotate(-180);
//                    arrowAnimation = new Timeline(new KeyFrame(Duration.millis(160),
//                        new KeyValue(arrow.rotateProperty(),
//                            0,
//                            Interpolator.EASE_BOTH),
//                        new KeyValue(arrowContainer.opacityProperty(),
//                            1,
//                            Interpolator.EASE_BOTH),
//                        new KeyValue(arrowContainer.translateYProperty(),
//                            0,
//                            Interpolator.EASE_BOTH)));
//                }
//                arrowAnimation.setOnFinished((finish) -> currentArrowRotation = arrow.getRotate());
//                arrowAnimation.play();
//
//            }
//
//            if (arrowContainer != null && arrowPane != null && container.getChildren()
//                .size() == 1 && !arrowPane.isVisible()) {
//                if (arrowAnimation != null && arrowAnimation.getStatus() == Status.RUNNING) {
//                    arrowAnimation.stop();
//                }
//                Label label = (Label) container.getChildren().get(0);
//                // dont change the padding if arrow is not showing
//                if (currentArrowRotation == 0) {
//                    StackPane.setMargin(label,
//                        new Insets(oldMargin == null ? 0 : oldMargin.getTop(),
//                            oldMargin == null || oldMargin.getRight() < 30 ? 30 : oldMargin
//                                .getRight(),
//                            oldMargin == null ? 0 : oldMargin.getBottom(),
//                            oldMargin == null || oldMargin.getLeft() < 30 ? 30 : oldMargin
//                                .getLeft()));
//                }
//
//                container.getChildren().add(arrowContainer);
//                arrowAnimation = new Timeline(new KeyFrame(Duration.millis(320),
//                    new KeyValue(arrowContainer.opacityProperty(),
//                        0,
//                        Interpolator.EASE_BOTH),
//                    new KeyValue(arrowContainer.translateYProperty(),
//                        getHeight() / 4,
//                        Interpolator.EASE_BOTH)));
//                arrowAnimation.setOnFinished((finish) -> {
//                    currentArrowRotation = -1;
//                    StackPane.setMargin(label, null);
//                });
//                arrowAnimation.play();
//            }
//        }
    }

}
