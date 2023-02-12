package visualizer.view.dialogue;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.function.Function;

public class AddEdgeDialogue {
    private final JPanel content;
    private final JTextField textField = new JTextField();
    private final JOptionPane dialog;

    {
        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(new JLabel("Enter Weight"));
        content.add(textField);

        dialog = new JOptionPane(content, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION) {
            @Override
            public void selectInitialValue() {
                textField.requestFocusInWindow();
            }
        };
    }

    public void show(Function<String, Boolean> action) {
        textField.setText("");

        JDialog d = dialog.createDialog(content, "Input");
        d.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                if (dialog.getValue() == null) return;
                if ((Integer) dialog.getValue() == JOptionPane.OK_OPTION) {
                    boolean reappearanceRequired = action.apply(textField.getText());
                    if (reappearanceRequired) show(action);
                }
            }
        });
        d.setVisible(true);
    }
}
