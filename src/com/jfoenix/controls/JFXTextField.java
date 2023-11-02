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

package com.jfoenix.controls;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.base.IFXLabelFloatControl;
import com.jfoenix.skins.JFXTextFieldSkin;
import com.jfoenix.validation.base.ValidatorBase;
import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.PaintConverter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * JFXTextField is the material design implementation of a text Field.
 *
 * @author Shadi Shaheen
 * @version 1.0
 * @since 2016-03-09
 */
public class JFXTextField extends TextField implements IFXLabelFloatControl {

    /**
     * {@inheritDoc}
     */
    public JFXTextField() {
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    public JFXTextField(String text) {
        super(text);
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new JFXTextFieldSkin<>(this);
    }

    private void initialize() {
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
        if ("dalvik".equals(System.getProperty("java.vm.name").toLowerCase())) {
            this.setStyle("-fx-skin: \"com.jfoenix.android.skins.JFXTextFieldSkinAndroid\";");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() {
        return USER_AGENT_STYLESHEET;
    }

    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
    /**
     * wrapper for validation properties / methods
     */
    protected ValidationControl validationControl = new ValidationControl(this);

    @Override
    public ValidatorBase getActiveValidator() {
        return validationControl.getActiveValidator();
    }

    @Override
    public ReadOnlyObjectProperty<ValidatorBase> activeValidatorProperty() {
        return validationControl.activeValidatorProperty();
    }

    @Override
    public ObservableList<ValidatorBase> getValidators() {
        return validationControl.getValidators();
    }

    @Override
    public void setValidators(ValidatorBase... validators) {
        validationControl.setValidators(validators);
    }

    @Override
    public boolean validate() {
        return validationControl.validate();
    }

    @Override
    public void resetValidation() {
        validationControl.resetValidation();
    }

    /**
     * An optional leading icon for the TextField
     */
    private ObjectProperty<Node> leadingGraphic = new SimpleObjectProperty<>();

    public Node getLeadingGraphic() {
        return leadingGraphic.get();
    }

    public ObjectProperty<Node> leadingGraphicProperty() {
        return leadingGraphic;
    }

    public void setLeadingGraphic(Node leadingGraphic) {
        this.leadingGraphic.set(leadingGraphic);
    }

    /**
     * An optional trailing icon for the TextField
     */
    private ObjectProperty<Node> trailingGraphic = new SimpleObjectProperty<>();

    public Node getTrailingGraphic() {
        return trailingGraphic.get();
    }

    public ObjectProperty<Node> trailingGraphicProperty() {
        return trailingGraphic;
    }

    public void setTrailingGraphic(Node trailingGraphic) {
        this.trailingGraphic.set(trailingGraphic);
    }

    /***************************************************************************
     *                                                                         *
     * Styleable Properties                                                    *
     *                                                                         *
     **************************************************************************/

    /**
     * Initialize the style class to 'jfx-text-field'.
     * <p>
     * This is the selector class from which CSS can be used to style
     * this control.
     */
    private static final String DEFAULT_STYLE_CLASS = "jfx-text-field";
    private static final String USER_AGENT_STYLESHEET = JFoenixResources.load("css/controls/jfx-text-field.css").toExternalForm();


    /**
     * set true to show a float the prompt text when focusing the field
     */
    private StyleableBooleanProperty labelFloat = new SimpleStyleableBooleanProperty(StyleableProperties.LABEL_FLOAT,
        JFXTextField.this,
        "lableFloat",
        false);

    @Override
    public final StyleableBooleanProperty labelFloatProperty() {
        return this.labelFloat;
    }

    @Override
    public final boolean isLabelFloat() {
        return this.labelFloatProperty().get();
    }

    @Override
    public final void setLabelFloat(final boolean labelFloat) {
        this.labelFloatProperty().set(labelFloat);
    }

    /**
     * default color used when the field is unfocused
     */
    private StyleableObjectProperty<Paint> unFocusColor = new SimpleStyleableObjectProperty<>(StyleableProperties.UNFOCUS_COLOR,
        JFXTextField.this,
        "unFocusColor",
        Color.rgb(77,
            77,
            77));

    @Override
    public Paint getUnFocusColor() {
        return unFocusColor == null ? Color.rgb(77, 77, 77) : unFocusColor.get();
    }

    @Override
    public StyleableObjectProperty<Paint> unFocusColorProperty() {
        return this.unFocusColor;
    }

    @Override
    public void setUnFocusColor(Paint color) {
        this.unFocusColor.set(color);
    }

    /**
     * default color used when the field is focused
     */
    private StyleableObjectProperty<Paint> focusColor = new SimpleStyleableObjectProperty<>(StyleableProperties.FOCUS_COLOR,
        JFXTextField.this,
        "focusColor",
        Color.valueOf("#4059A9"));

    @Override
    public Paint getFocusColor() {
        return focusColor == null ? Color.valueOf("#4059A9") : focusColor.get();
    }

    @Override
    public StyleableObjectProperty<Paint> focusColorProperty() {
        return this.focusColor;
    }

    @Override
    public void setFocusColor(Paint color) {
        this.focusColor.set(color);
    }

    /**
     * disable animation on validation
     */
    private StyleableBooleanProperty disableAnimation = new SimpleStyleableBooleanProperty(StyleableProperties.DISABLE_ANIMATION,
        JFXTextField.this,
        "disableAnimation",
        false);

    @Override
    public final StyleableBooleanProperty disableAnimationProperty() {
        return this.disableAnimation;
    }

    @Override
    public final Boolean isDisableAnimation() {
        return disableAnimation != null && this.disableAnimationProperty().get();
    }

    @Override
    public final void setDisableAnimation(final Boolean disabled) {
        this.disableAnimationProperty().set(disabled);
    }


    private static class StyleableProperties {
        private static final CssMetaData<JFXTextField, Paint> UNFOCUS_COLOR = new CssMetaData<JFXTextField, Paint>(
            "-jfx-unfocus-color",
            PaintConverter.getInstance(),
            Color.valueOf("#A6A6A6")) {
            @Override
            public boolean isSettable(JFXTextField control) {
                return control.unFocusColor == null || !control.unFocusColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(JFXTextField control) {
                return control.unFocusColorProperty();
            }
        };
        private static final CssMetaData<JFXTextField, Paint> FOCUS_COLOR = new CssMetaData<JFXTextField, Paint>(
            "-jfx-focus-color",
            PaintConverter.getInstance(),
            Color.valueOf("#3f51b5")) {
            @Override
            public boolean isSettable(JFXTextField control) {
                return control.focusColor == null || !control.focusColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(JFXTextField control) {
                return control.focusColorProperty();
            }
        };
        private static final CssMetaData<JFXTextField, Boolean> LABEL_FLOAT = new CssMetaData<JFXTextField, Boolean>(
            "-jfx-label-float",
            BooleanConverter.getInstance(),
            false) {
            @Override
            public boolean isSettable(JFXTextField control) {
                return control.labelFloat == null || !control.labelFloat.isBound();
            }

            @Override
            public StyleableBooleanProperty getStyleableProperty(JFXTextField control) {
                return control.labelFloatProperty();
            }
        };

        private static final CssMetaData<JFXTextField, Boolean> DISABLE_ANIMATION =
            new CssMetaData<JFXTextField, Boolean>("-jfx-disable-animation",
                BooleanConverter.getInstance(), false) {
                @Override
                public boolean isSettable(JFXTextField control) {
                    return control.disableAnimation == null || !control.disableAnimation.isBound();
                }

                @Override
                public StyleableBooleanProperty getStyleableProperty(JFXTextField control) {
                    return control.disableAnimationProperty();
                }
            };


        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                TextField.getClassCssMetaData());
            Collections.addAll(styleables, UNFOCUS_COLOR, FOCUS_COLOR, LABEL_FLOAT, DISABLE_ANIMATION);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.CHILD_STYLEABLES;
    }
}
