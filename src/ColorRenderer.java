import javax.swing.*;
import javax.swing.table.*;
import java.awt.Color;
import java.util.*;
import java.awt.Point;
import java.awt.Component;

class ColorRenderer
{
    private JTable table;
    private Map colors;
    private Point location;

    public ColorRenderer(JTable table)
    {
            this.table = table;
            colors = new HashMap();
            location = new Point();
    }

    public void setBackground(Component c, int row, int column)
    {
            //  Don't override the background color of a selected cell

            if ( table.isCellSelected(row, column) ) return;

            //  The default render does not reset the background color
            //  that was set for the previous cell, so reset it here

            if (c instanceof DefaultTableCellRenderer)
            {
                    c.setBackground( table.getBackground() );
            }

            //  In case columns have been reordered, convert the column number

            column = table.convertColumnIndexToModel(column);

            //  Get cell color

            Object key = getKey(row, column);
            Object o = colors.get( key );

            if (o != null)
            {
                    c.setBackground( (Color)o );
                    return;
            }
    }

    public void setCellColor(int row, int column, Color color)
    {
            Point key = new Point(row, column);
            colors.put(key, color);
    }

    private Object getKey(int row, int column)
    {
            location.x = row;
            location.y = column;
            return location;
    }
}