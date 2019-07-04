package com.github.lucacampanella.callgraphflows.graphics.components;

import com.github.lucacampanella.callgraphflows.graphics.utils.GUtils;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GBaseTextComponent extends GBaseGraphicSimpleComponent {

    public static final Color LESS_IMPORTANT_TEXT_COLOR = Color.GRAY;

    static final int BORDER_DIM = 4; //dimension of border between text and external line
    String text;
    Color textColor = Color.BLACK;
    boolean drawBox = false;
    protected Color backgroundColor = Color.WHITE;

    public GBaseTextComponent(String text) {
        this.text = text;
    }

    @Override
    public void draw(SVGGraphics2D g2) {
        Dimension dim = getDimensions(g2);
        Rectangle rect = new Rectangle(getStartX(), getStartY(), dim.width, dim.height);
        GUtils.fillWithColor(g2, rect, getBackgroundColor());
        if(drawBox) {
            g2.draw(rect);
        }

        Color backupColor = g2.getColor();
        g2.setColor(getTextColor());
        int currY = getStartY() + getBorderDim();
        for (String line : getDisplayText().split("\n")) {
            g2.drawString(line, getStartX() + getBorderDim(), currY + g2.getFontMetrics().getAscent());
            currY += g2.getFontMetrics().getHeight();
        }
        g2.setColor(backupColor);
    }

    @Override
    protected int computeWidth(SVGGraphics2D g2) {
        //todo: find better way to compute this, just the length of the string is wrong
        int width = 0;
        Rectangle2D stringBounds;
        for (String line : getDisplayText().split("\n")) {
            stringBounds = g2.getFont().getStringBounds(line, g2.getFontRenderContext());
            int newWidth = GUtils.doubleToInt(stringBounds.getWidth());
            if(newWidth > width) {
                width = newWidth;
            }
        }

        return width + 2*getBorderDim();
    }

    @Override
    protected int computeHeight(SVGGraphics2D g2) {
        int height = 0;
        Rectangle2D stringBounds;
        for (String line : getDisplayText().split("\n")) {
            stringBounds = g2.getFont().getStringBounds(line, g2.getFontRenderContext());
            height += GUtils.doubleToInt(stringBounds.getHeight());
        }

        return height + 2*getBorderDim();
    }

    public int getBorderDim() {
        return BORDER_DIM;
    }

    public String getDisplayText(){
        return getText();
    }

    public String getText(){
        return text;
    }

    public void setText(String text) {
        this.text = text;
        recomputeDimensions = true;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public boolean isDrawBox() {
        return drawBox;
    }

    public void setDrawBox(boolean drawBox) {
        this.drawBox = drawBox;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
