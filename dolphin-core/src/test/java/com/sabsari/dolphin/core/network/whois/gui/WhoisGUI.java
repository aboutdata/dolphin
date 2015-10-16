package com.sabsari.dolphin.core.network.whois.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import com.sabsari.dolphin.core.network.whois.Whois;
import com.sabsari.dolphin.core.network.whois.Whois.SearchFor;
import com.sabsari.dolphin.core.network.whois.Whois.SearchIn;

public class WhoisGUI extends JFrame {
    
    private static final long serialVersionUID = 2101151775131533241L;
    
    private JTextField searchString;
    private JTextArea names;
    private JButton findButton;
    private ButtonGroup searchIn;
    private ButtonGroup searchFor;
    private JCheckBox exactMatch;
    private JTextField chosenServer;
    private Whois server;
    
    private void init(Whois whois) {
        searchString = new JTextField(30);
        names = new JTextArea(15, 80);
        findButton = new JButton("Find");
        searchIn = new ButtonGroup();
        searchFor = new ButtonGroup();
        exactMatch = new JCheckBox("Exact Match", true);
        chosenServer = new JTextField();
        this.server = whois;
    }
    
    public WhoisGUI(Whois whois) {
        super("Whois");
        init(whois);
        
        Container pane = this.getContentPane();
        Font f = new Font("Monospaced", Font.PLAIN, 12);
        names.setFont(f);
        names.setEditable(false);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 1, 10, 10));
        JScrollPane jsp = new JScrollPane(names);
        centerPanel.add(jsp);
        pane.add("Center", centerPanel);
        
        JPanel northPanel = new JPanel();
        JPanel northPanelTop = new JPanel();
        northPanelTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        northPanelTop.add(new JLabel("Whois: "));
        northPanelTop.add("North", searchString);
        northPanelTop.add(exactMatch);
        northPanelTop.add(findButton);
        northPanel.setLayout(new BorderLayout(2, 1));
        northPanel.add("North", northPanelTop);
        JPanel northPanelBottom = new JPanel();
        northPanelBottom.setLayout(new GridLayout(1, 3, 5, 5));
        northPanelBottom.add(initRecordType());
        northPanelBottom.add(initSearchFields());
        northPanelBottom.add(initServerChoice());
        northPanel.add("Center", northPanelBottom);
        
        pane.add("North", northPanel);
        
        ActionListener al = new LookupNames();
        findButton.addActionListener(al);
        searchString.addActionListener(al);
    }
    
    private JPanel initRecordType() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(6, 2, 5, 2));
        p.add(new JLabel("Search for:"));
        p.add(new JLabel(""));
        
        JRadioButton any = new JRadioButton("Any", true);
        any.setActionCommand("Any");
        searchFor.add(any);
        p.add(any);
        
        for (SearchFor val : SearchFor.values()) {
            p.add(this.makeRadioButton(searchFor, val.getLabel()));            
        }
        
        return p;
    }
    
    private JPanel initSearchFields() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(6, 1, 5, 2));
        p.add(new JLabel("Search In: "));
        
        JRadioButton all = new JRadioButton("All", true);
        all.setActionCommand("All");
        searchIn.add(all);
        p.add(all);
        
        for (SearchIn val : SearchIn.values()) {
            p.add(this.makeRadioButton(searchIn, val.getLabel()));            
        }
        
        return p;
    }

    private JPanel initServerChoice() {
        final JPanel p = new JPanel();
        p.setLayout(new GridLayout(6, 1, 5, 2));
        p.add(new JLabel("Search At: "));
        
        chosenServer.setText(server.getHost().getHostName());
        p.add(chosenServer);
        chosenServer.addActionListener(new ActionListener() {            
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    server = new Whois(chosenServer.getText());
                }
                catch (UnknownHostException ex) {
                    JOptionPane.showMessageDialog(p, ex.getMessage(),
                            "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return p;
    }
    
    private JRadioButton makeRadioButton(ButtonGroup group, String label) {
        JRadioButton button = new JRadioButton(label, false);
        button.setActionCommand(label);
        group.add(button);
        return button;
    }
    
    private class LookupNames implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            names.setText("");
            SwingWorker<String, Object> worker = new Lookup();
            worker.execute();
        }
    }
    
    private class Lookup extends SwingWorker<String, Object> {

        @Override
        protected String doInBackground() throws Exception {
            SearchIn group = SearchIn.ALL;
            SearchFor category = SearchFor.ANY;
            
            String searchForLabel = searchFor.getSelection().getActionCommand();
            String searchInLabel = searchIn.getSelection().getActionCommand();
            
            for (SearchIn grp : SearchIn.values()) {
                if (grp.getLabel().equals(searchInLabel)) {
                    group = grp;
                    break;
                }
            }
            
            for (SearchFor ctgy : SearchFor.values()) {
                if (ctgy.getLabel().equals(searchForLabel)) {
                    category = ctgy;
                    break;
                }
            }
            
            server.setHost(chosenServer.getText());
            return server.lookUpNames(searchString.getText(), category, group, exactMatch.isSelected());
        }
        
        @Override
        protected void done() {
            try {
                names.setText(get());
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(WhoisGUI.this, ex.getMessage(),
                        "Lookup Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            Whois whois = new Whois();
            WhoisGUI g = new WhoisGUI(whois);
            g.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            g.pack();
            EventQueue.invokeLater(new FrameShower(g));
        }
        catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Could not locate default host " + Whois.DEFAULT_HOST,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static class FrameShower implements Runnable {

        private final Frame frame;
        
        FrameShower(Frame frame) {
            this.frame = frame;
        }
        
        @Override
        public void run() {
            frame.setVisible(true);
        }
    }
}
