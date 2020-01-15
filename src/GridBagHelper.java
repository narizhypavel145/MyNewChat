import javax.swing.*;
import java.awt.*;

public class GridBagHelper {
    // настраиваемый объект GridBagConstraints
    private static GridBagConstraints constraints;
    private static GridBagHelper instance;

    private GridBagHelper(){
        resetSettings();
    }

    public static GridBagHelper getInstance(){
        if (instance == null)
            instance = new GridBagHelper();
        return instance;
    }

    // возвращает настроенный объект GridBagConstraints
    public GridBagConstraints get() {
        return constraints;
    }
    // двигается на следующую ячейку
    public GridBagHelper nextCell() {
        constraints.gridx++;
        return this;
    }
    // двигается на следующий ряд
    public GridBagHelper nextRow() {
        constraints.gridy++;
        constraints.gridx = 0;
        return this;
    }
    // раздвигает ячейку до конца строки
    public GridBagHelper span() {
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        return this;
    }
    // заполняет ячейку по горизонтали
    public GridBagHelper fillHorizontally() {
        constraints.fill = GridBagConstraints.HORIZONTAL;
        return this;
    }
    // вставляет распорку справа
    public GridBagHelper gap(int size) {
        constraints.insets.right = size;
        return this;
    }

    public GridBagHelper spanY() {
        constraints.gridheight = GridBagConstraints.REMAINDER;
        return this;
    }


    public GridBagHelper fillBoth() {
        constraints.fill = GridBagConstraints.BOTH;
        return this;
    }

    public GridBagHelper alignLeft() {
        constraints.anchor = GridBagConstraints.LINE_START;
        return this;
    }

    public GridBagHelper alignRight() {
        constraints.anchor = GridBagConstraints.LINE_END;
        return this;
    }

    public GridBagHelper setInsets(int left, int top, int right, int bottom) {
        Insets i = new Insets(top, left, bottom, right);
        constraints.insets = i;
        return this;
    }

    public GridBagHelper setWeights(float horizontal, float vertical) {
        constraints.weightx = horizontal;
        constraints.weighty = vertical;
        return this;
    }

    public void insertEmptyRow(Container c, int height) {
        Component comp = Box.createVerticalStrut(height);
        nextCell().nextRow().fillHorizontally().span();
        c.add(comp, get());
        nextRow();
    }

    public void insertEmptyFiller(Container c) {
        Component comp = Box.createGlue();
        nextCell().nextRow().fillBoth().span().spanY().setWeights(1.0f, 1.0f);
        c.add(comp, get());
        nextRow();
    }

    public void resetSettings(){
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
    }
}