package honkot.java.foundation.training;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hhonda on 2016-11-18.
 */
public class Chapter6_3 implements ChapterBase {

    @Override
    public void main() {
        JFrame frame = new JFrame("Quote Options");
        QuoteOptionsPanel panel = new QuoteOptionsPanel();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private class QuoteOptionsPanel extends JPanel {
        private JLabel quote;
        private JRadioButton comedy, philosophy, carpentry;
        private JCheckBox bold, italic;
        private String comedyQuote, philosophyQuote, carpentryQuote;

        public QuoteOptionsPanel() {
            comedyQuote = "Take my wife, please.";
            philosophyQuote = "I think, therefore I am.";
            carpentryQuote = "Measure twice. Cut once.";

            quote = new JLabel(comedyQuote);
            setQuote();

            comedy = new JRadioButton("Comedy", true);
            comedy.setBackground(Color.green);
            philosophy = new JRadioButton("Philosophy");
            philosophy.setBackground(Color.green);
            carpentry = new JRadioButton("Carpentry");
            carpentry.setBackground(Color.green);

            ButtonGroup group = new ButtonGroup();
            group.add(comedy);
            group.add(philosophy);
            group.add(carpentry);

            bold = new JCheckBox("Bold");
            italic = new JCheckBox("Italic");

            QuoteListener listener = new QuoteListener();
            comedy.addActionListener(listener);
            philosophy.addActionListener(listener);
            carpentry.addActionListener(listener);
            bold.addActionListener(listener);
            italic.addActionListener(listener);

            add(quote);
            add(comedy);
            add(philosophy);
            add(carpentry);
            add(bold);
            add(italic);

            setBackground(Color.green);
            setPreferredSize(new Dimension(300, 100));

        }

        private void setQuote() {
            int fontStyle = Font.PLAIN; // 0
            if (bold != null && bold.isSelected()) fontStyle |= Font.BOLD;
            if (italic != null && italic.isSelected()) fontStyle |= Font.ITALIC;
            System.out.println(fontStyle);

            quote.setFont(new Font("Helvetica", fontStyle, 24));
        }

        private class QuoteListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                Object source = event.getSource();

                if (source == comedy) {
                    quote.setText(comedyQuote);
                } else if (source == philosophy) {
                    quote.setText(philosophyQuote);
                } else if (source == carpentry) {
                    quote.setText(carpentryQuote);
                } else if (source == bold) {
                    setQuote();
                } else if (source == italic) {
                    setQuote();
                }
            }
        }
    }
}
