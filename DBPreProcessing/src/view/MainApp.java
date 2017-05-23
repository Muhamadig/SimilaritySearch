package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JInternalFrame;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainApp extends JFrame {
	private JTextField files_text;
    private String lastPath;
	private JTable FVs_table;
    private File[] files;



	public MainApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("PreProccessing");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.setBackground(Color.WHITE);
		getContentPane().add(tabbedPane, BorderLayout.WEST);
		
		int tabWidth=tabbedPane.getWidth();
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(700, 500));
//		panel.setBounds(tabWidth, 0, this.getWidth()-tabWidth, 400);
        tabbedPane.addTab("Process All Texts", null, panel,"Build Frequency Vectors for All Texts");
        panel.setLayout(null);
        lastPath="/";
        
        files_text = new JTextField();
        files_text.setEditable(false);
        files_text.setBackground(Color.WHITE);
        files_text.setBounds(10, 38, 282, 20);
        panel.add(files_text);
        files_text.setColumns(10);
        JButton browse_btn = new JButton("Browse Files");
       
       
        browse_btn.setBounds(296, 37, 114, 23);
        panel.add(browse_btn);
        
        JLabel upload_info = new JLabel("");
        upload_info.setFont(new Font("Tahoma", Font.PLAIN, 14));
        upload_info.setBounds(10, 69, 282, 14);
        panel.add(upload_info);
        
        JButton proc_btn = new JButton("Begin Texts Processing");
        
        proc_btn.setBounds(147, 108, 203, 23);
        proc_btn.setEnabled(false);
        panel.add(proc_btn);
        
        JScrollPane fvs_scrl = new JScrollPane();
        fvs_scrl.setBounds(10, 138, 680, 312);
        panel.add(fvs_scrl);
        
        String[] doc_columnNames = {"#", "Text Name", "# of Rep. Words", "# of frequencies","# stop words" };
		Object[][] doc_data = {};
		FVs_table = new JTable();
		FVs_table.setModel(new MyTableModel(doc_columnNames, doc_data));
		FVs_table.setFillsViewportHeight(true);
		FVs_table.setSurrendersFocusOnKeystroke(true);
		FVs_table.setShowVerticalLines(false);
		FVs_table.setRowHeight(30);
		FVs_table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		fvs_scrl.setViewportView(FVs_table);
		FVs_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		FVs_table.setBackground(Color.WHITE);
		FVs_table.getColumnModel().getColumn(0).setPreferredWidth(30);
		FVs_table.getColumnModel().getColumn(1).setPreferredWidth(380);
		FVs_table.getColumnModel().getColumn(2).setPreferredWidth(90);
		FVs_table.getColumnModel().getColumn(3).setPreferredWidth(90);
		FVs_table.getColumnModel().getColumn(4).setPreferredWidth(90);


//        tabbedPane.setMnemonicAt(0, 49);
		
		JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Merge All Texts", null, panel_1, "Build and Sort The Global Frequency Vector");
		
		JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Find The Common Words", null, panel_2,"Find The Common Words");
		
		JPanel panel_3 = new JPanel();
        tabbedPane.addTab("Remove The Common Words From All Texts", null, panel_3,"Remove The Common Words From All The frequency Vectors of the Texts");
       
		/*
        JXTabbedPane tabbedPane = new JXTabbedPane(JTabbedPane.LEFT);
        AbstractTabRenderer renderer = (AbstractTabRenderer)tabbedPane.getTabRenderer();
        renderer.setPrototypeText("This text is a prototype");
        renderer.setHorizontalTextAlignment(SwingConstants.LEADING);
		
        JPanel panel = new JPanel();
        tabbedPane.addTab("Process All Texts", null, createEmptyPanel(),"Build Frequency Vectors for All Texts");
		
		JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Merge All Texts", null, createEmptyPanel(), "Build and Sort The Global Frequency Vector");
		
		JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Find The Common Words", null, createEmptyPanel(),"Find The Common Words");
		
		JPanel panel_3 = new JPanel();
        tabbedPane.addTab("Remove The Common Words From All Texts", null, createEmptyPanel(),"Remove The Common Words From All The frequency Vectors of the Texts");
       
//       add(tabbedPane);
        */
        setBounds(0, 0, 1000, 500);
        this.setLocationRelativeTo(null);
        
        browse_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                proc_btn.setEnabled(false);

                files=BrowseFiles();
        		
        		files_text.setText(lastPath=files[0].getParentFile().toString());
        		int files_size=files.length;
        		if(files_size==0){
        			upload_info.setText("Warning :No files was uploaded");
        	        proc_btn.setEnabled(false);
        		}else if(files_size>0){
        			upload_info.setText("Done :Number of uploaded files: "+files_size );
        	        proc_btn.setEnabled(true);

        		}
        		
        	}
        });
    }
	 protected File[] BrowseFiles() {
		 JFileChooser chooser = new JFileChooser();
         chooser.setCurrentDirectory(new java.io.File(lastPath));
         chooser.setDialogTitle("Browse the folder to process");
         chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
         chooser.setAcceptAllFileFilterUsed(false);

         chooser.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "[ HTML,Doc,Docx,PDF]";
			}
			
			@Override
			public boolean accept(File f) {
				  if (f.isDirectory()) {
			           return true;
			       } else {
			           String filename = f.getName();
			           return filename.endsWith(".doc") || filename.endsWith(".docx") ||filename.endsWith(".pdf") ||filename.endsWith(".html") ;
			       }			
			}
		});
         chooser.setMultiSelectionEnabled(true);
         chooser.showOpenDialog(null);

         File[] files = chooser.getSelectedFiles();
        
		return files;
	}
	private JPanel createEmptyPanel() {
	        JPanel dummyPanel = new JPanel() {

	            @Override
	            public Dimension getPreferredSize() {
	                return isPreferredSizeSet() ?
	                            super.getPreferredSize() : new Dimension(400, 300);
	            }

	        };
	        return dummyPanel;
	    }
	 
	 
	 
	 
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		new MainApp().setVisible(true);
            }
        });
    }
    
    class JXTabbedPane extends JTabbedPane {

        private ITabRenderer tabRenderer = new DefaultTabRenderer();

        public JXTabbedPane() {
            super();
        }

        public JXTabbedPane(int tabPlacement) {
            super(tabPlacement);
        }

        public JXTabbedPane(int tabPlacement, int tabLayoutPolicy) {
            super(tabPlacement, tabLayoutPolicy);
        }

        public ITabRenderer getTabRenderer() {
            return tabRenderer;
        }

        public void setTabRenderer(ITabRenderer tabRenderer) {
            this.tabRenderer = tabRenderer;
        }

        @Override
        public void addTab(String title, Component component) {
            this.addTab(title, null, component, null);
        }

        @Override
        public void addTab(String title, Icon icon, Component component) {
            this.addTab(title, icon, component, null);
        }

        @Override
        public void addTab(String title, Icon icon, Component component, String tip) {
            super.addTab(title, icon, component, tip);
            int tabIndex = getTabCount() - 1;
            Component tab = tabRenderer.getTabRendererComponent(this, title, icon, tabIndex);
            super.setTabComponentAt(tabIndex, tab);
        }
    }

    interface ITabRenderer {

        public Component getTabRendererComponent(JTabbedPane tabbedPane, String text, Icon icon, int tabIndex);

    }

    abstract class AbstractTabRenderer implements ITabRenderer {

        private String prototypeText = "";
        private Icon prototypeIcon = UIManager.getIcon("OptionPane.informationIcon");
        private int horizontalTextAlignment = SwingConstants.CENTER;
        private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

        public AbstractTabRenderer() {
            super();
        }

        public void setPrototypeText(String text) {
            String oldText = this.prototypeText;
            this.prototypeText = text;
            firePropertyChange("prototypeText", oldText, text);
        }

        public String getPrototypeText() {
            return prototypeText;
        }

        public Icon getPrototypeIcon() {
            return prototypeIcon;
        }

        public void setPrototypeIcon(Icon icon) {
            Icon oldIcon = this.prototypeIcon;
            this.prototypeIcon = icon;
            firePropertyChange("prototypeIcon", oldIcon, icon);
        }

        public int getHorizontalTextAlignment() {
            return horizontalTextAlignment;
        }

        public void setHorizontalTextAlignment(int horizontalTextAlignment) {
            this.horizontalTextAlignment = horizontalTextAlignment;
        }

        public PropertyChangeListener[] getPropertyChangeListeners() {
            return propertyChangeSupport.getPropertyChangeListeners();
        }

        public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
            return propertyChangeSupport.getPropertyChangeListeners(propertyName);
        }

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            propertyChangeSupport.addPropertyChangeListener(listener);
        }

        public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
            propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
        }

        protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
            PropertyChangeListener[] listeners = getPropertyChangeListeners();
            for (int i = listeners.length - 1; i >= 0; i--) {
                listeners[i].propertyChange(new PropertyChangeEvent(this, propertyName, oldValue, newValue));
            }
        }
    }

    class DefaultTabRenderer extends AbstractTabRenderer implements PropertyChangeListener {

        private Component prototypeComponent;

        public DefaultTabRenderer() {
            super();
            prototypeComponent = generateRendererComponent(getPrototypeText(), getPrototypeIcon(), getHorizontalTextAlignment());
            addPropertyChangeListener(this);
        }

        private Component generateRendererComponent(String text, Icon icon, int horizontalTabTextAlignmen) {
            JPanel rendererComponent = new JPanel(new GridBagLayout());
            rendererComponent.setOpaque(false);

            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(2, 4, 2, 4);
            c.fill = GridBagConstraints.HORIZONTAL;
            rendererComponent.add(new JLabel(icon), c);

            c.gridx = 1;
            c.weightx = 1;
            rendererComponent.add(new JLabel(text, horizontalTabTextAlignmen), c);

            return rendererComponent;
        }

        @Override
        public Component getTabRendererComponent(JTabbedPane tabbedPane, String text, Icon icon, int tabIndex) {
            Component rendererComponent = generateRendererComponent(text, icon, getHorizontalTextAlignment());
            int prototypeWidth = prototypeComponent.getPreferredSize().width;
            int prototypeHeight = prototypeComponent.getPreferredSize().height;
            rendererComponent.setPreferredSize(new Dimension(prototypeWidth, prototypeHeight));
            return rendererComponent;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();
            if ("prototypeText".equals(propertyName) || "prototypeIcon".equals(propertyName)) {
                this.prototypeComponent = generateRendererComponent(getPrototypeText(), getPrototypeIcon(), getHorizontalTextAlignment());
            }
        }
    }
}