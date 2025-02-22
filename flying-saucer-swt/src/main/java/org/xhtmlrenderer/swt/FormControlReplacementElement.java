/*
 * {{{ header & license
 * Copyright (c) 2007 Vianney le Clément
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */
package org.xhtmlrenderer.swt;

import org.xhtmlrenderer.css.style.CalculatedStyle;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.swt.simple.SWTFormControl;

import java.awt.*;

/**
 * ReplacementElement for a FormControl.
 *
 * @author Vianney le Clément
 */
public class FormControlReplacementElement implements ReplacedElement {
    private final SWTFormControl _swtControl;
    private final Point _location = new Point(0, 0);
    private int _width, _height;

    public FormControlReplacementElement(SWTFormControl swtControl) {
        _swtControl = swtControl;
    }

    public SWTFormControl getControl() {
        return _swtControl;
    }

    @Override
    public void detach(LayoutContext c) {
        // nothing to do
    }

    @Override
    public int getIntrinsicHeight() {
        return _height;
    }

    @Override
    public int getIntrinsicWidth() {
        return _width;
    }

    /**
     * Recalculate the size of the control based on the css lengths. These
     * lengths can be -1 if the size has to be determined automatically.
     */
    public void calculateSize(LayoutContext c, CalculatedStyle style,
            int cssWidth, int cssHeight) {
        _width = cssWidth;
        _height = cssHeight;
        if (_width < 0) {
            _width = _swtControl.getIdealWidth();
        }
        if (_height < 0) {
            _height = _swtControl.getIdealHeight();
        }
        _swtControl.getSWTControl().setSize(_width, _height);
    }

    @Override
    public Point getLocation() {
        return _location;
    }

    @Override
    public void setLocation(int x, int y) {
        _location.setLocation(x, y);
        BasicRenderer parent = (BasicRenderer) _swtControl.getSWTControl()
            .getParent();
        org.eclipse.swt.graphics.Point origin = parent.getOrigin();
        _swtControl.getSWTControl().setLocation(x - origin.x, y - origin.y);
    }

    @Override
    public boolean isRequiresInteractivePaint() {
        return true;
    }

    @Override
    public int getBaseline() {
        return 0;
    }

    @Override
    public boolean hasBaseline() {
        return false;
    }

}
