package GUI;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import GUI.MainWindow;

import PapiManager.*;
import Model.*;

public class MainWindow {

	private String SORT_TYPE_ASCENDING = "Ascending";
	private String SORT_TYPE_DESCENDING = "Descending";
	
	private String APP_BUBBLESORT = "Bubble sort";
	private String APP_QUICKSORT = "Quick sort";
	
	private String APP_BUBBLESORT_CMD = "./SortingApp b 100";
	private String APP_QUICKSORT_CMD = "./SortingApp q 100";
	
	private JFrame frame;
	private JTable tblAppsStanding;
	private JComboBox<String> cbxComponent;
	private JComboBox<String> cbxEvent;
	private JComboBox<String> cbxSortType;
	private JComboBox<String> cbxApplication;
	
	private ArrayList<PapiComponentInfo> _componentsInfo;
	private Dictionary<String, Dictionary<String, TotalEventResult>> _applicationResults;
	
	public MainWindow()
	{
		initialize();
		InitializeWindowListener();
		InitializeMembers();
		InitializePapiManager();
		InitializeComponentsComboBox();
		InitializeSortTypeComboBox();
		InitializeAppsControls();
		
		frame.setVisible(true);
	}

	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 253);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblConfiguration = new JLabel("Configuration");
		lblConfiguration.setBounds(75, 12, 100, 15);
		panel.add(lblConfiguration);
		
		JLabel lblComponent = new JLabel("Component:");
		lblComponent.setBounds(12, 39, 90, 15);
		panel.add(lblComponent);
		
		JLabel lblEvent = new JLabel("Event:");
		lblEvent.setBounds(12, 71, 90, 15);
		panel.add(lblEvent);
		
		cbxComponent = new JComboBox<String>();
		cbxComponent.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	InitializeEventsComboBox(cbxComponent.getSelectedItem().toString());
		    }
		});
		cbxComponent.setBounds(103, 34, 120, 24);
		panel.add(cbxComponent);
		
		cbxEvent = new JComboBox<String>();
		cbxEvent.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	UpdateTable();
		    }
		});
		cbxEvent.setBounds(103, 66, 120, 24);
		panel.add(cbxEvent);
		
		cbxSortType = new JComboBox<String>();
		cbxSortType.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	UpdateTable();
		    }
		});
		cbxSortType.setBounds(103, 100, 120, 24);
		panel.add(cbxSortType);
		
		JLabel lblSortType = new JLabel("Sort type:");
		lblSortType.setBounds(12, 105, 90, 15);
		panel.add(lblSortType);
		
		tblAppsStanding = new JTable();
		tblAppsStanding.setBounds(249, 30, 228, 138);
		tblAppsStanding.setVisible(true);
		panel.add(tblAppsStanding);
		
		JLabel lblRunRegisteredApplication = new JLabel("Run registered application:");
		lblRunRegisteredApplication.setBounds(12, 190, 200, 15);
		panel.add(lblRunRegisteredApplication);
		
		cbxApplication = new JComboBox<String>();
		cbxApplication.setBounds(216, 185, 120, 24);
		panel.add(cbxApplication);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RunMeasurement();
			}
		});
		btnRun.setBounds(360, 185, 117, 25);
		panel.add(btnRun);
		
		JLabel lblApplicationsStanding = new JLabel("Applications standing");
		lblApplicationsStanding.setBounds(295, 12, 160, 15);
		panel.add(lblApplicationsStanding);
	}
	
	private void InitializeWindowListener()
	{
		frame.addWindowListener(new java.awt.event.WindowAdapter()
		{
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent)
		    {
		        if (JOptionPane.showConfirmDialog(frame, "Are you sure to close this window?", "Really Closing?",
		        		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
		        {
		            ClosePapiManager();
		        	System.exit(0);
		        }
		    }
		});
	}
	
	private void InitializeMembers()
	{
		_applicationResults = new Hashtable<String, Dictionary<String, TotalEventResult>>();
		
		_applicationResults.put(APP_BUBBLESORT, new Hashtable<String, TotalEventResult>());
		_applicationResults.put(APP_QUICKSORT, new Hashtable<String, TotalEventResult>());
	}
	
	private void InitializePapiManager()
	{
		JPapiManager.Init();
	}
		
	private void InitializeComponentsComboBox()
	{
		_componentsInfo = JPapiManager.GetComponentsInfo();
		
		for(int i = 0; i < _componentsInfo.size(); i++)
		{
			cbxComponent.addItem(_componentsInfo.get(i).GetName());
		}
	}
	
	private void InitializeEventsComboBox(String componentName)
	{
		cbxEvent.removeAllItems();
		
		int componentIndex = 0;
		while(componentIndex < _componentsInfo.size() && !_componentsInfo.get(componentIndex).GetName().equals(componentName))
		{
			componentIndex++;
		}
		
		if(componentIndex == _componentsInfo.size())
			return;
		
		for(int i = 0; i < _componentsInfo.get(componentIndex).GetEvents().size(); i++)
		{
			cbxEvent.addItem(_componentsInfo.get(componentIndex).GetEvents().get(i).GetName());
		}
	}
	
	private void InitializeSortTypeComboBox()
	{
		cbxSortType.addItem(SORT_TYPE_ASCENDING);
		cbxSortType.addItem(SORT_TYPE_DESCENDING);
	}
	
	private void InitializeAppsControls()
	{
		cbxApplication.addItem(APP_BUBBLESORT);
		cbxApplication.addItem(APP_QUICKSORT);
	}

	private void ClosePapiManager()
	{
		JPapiManager.Close();
	}

	private void RunMeasurement()
	{
		ArrayList<PapiEventInfo> choosenEventsArray = new ArrayList<PapiEventInfo>();
		choosenEventsArray.add(GetChoosenEvent());
		
		JPapiManager.StartMeasure(choosenEventsArray);
		
		if(cbxApplication.getSelectedItem().equals(APP_BUBBLESORT))
		{
			RunExternalCommnad(APP_BUBBLESORT_CMD);
		}
		if(cbxApplication.getSelectedItem().equals(APP_QUICKSORT))
		{
			RunExternalCommnad(APP_QUICKSORT_CMD);
		}
		
		UpdateResults(cbxApplication.getSelectedItem().toString(), JPapiManager.StopMeasure());
		UpdateTable();
	}

	private PapiEventInfo GetChoosenEvent()
	{
		PapiEventInfo result = null;
		
		for(int i = 0; i < _componentsInfo.size(); i++)
		{
			if(_componentsInfo.get(i).GetName().equals(cbxComponent.getSelectedItem()))
			{
				for(int j = 0; j < _componentsInfo.get(i).GetEvents().size(); j++)
				{
					if(_componentsInfo.get(i).GetEvents().get(j).GetName().equals(cbxEvent.getSelectedItem()))
					{
						result = _componentsInfo.get(i).GetEvents().get(j);
						break;
					}
				}
				break;
			}
		}
		
		return result;
	}
	
	private void UpdateResults(String application, ArrayList<PapiEventResult> results)
	{
		for(int i = 0; i  < results.size(); i++)
		{
			TotalEventResult ter = _applicationResults.get(application).get(results.get(i).GetName());
			if(ter == null)
			{
				ter = new TotalEventResult();
				ter.AddResult(results.get(i).GetResult());
				_applicationResults.get(application).put(results.get(i).GetName(), ter);
			}
			else
			{
				ter.AddResult(results.get(i).GetResult());
			}
		}
	}
	
	private void UpdateTable()
	{
		String[] columnNames = {"App name", "Avr result", "# of runs"};
        Object[][] data =
        {
        	GetTableRowData(APP_BUBBLESORT),
        	GetTableRowData(APP_QUICKSORT)
        };
		
		tblAppsStanding.setModel(new DefaultTableModel(data, columnNames));
	}
	
	private Object[] GetTableRowData(String appName)
	{
		Dictionary<String, TotalEventResult> dict = _applicationResults.get(appName);
		if(dict != null && cbxEvent.getSelectedItem() != null)
		{
			TotalEventResult ter = _applicationResults.get(appName).get(cbxEvent.getSelectedItem().toString());
			
			if(ter != null)
			{
				int noOfRuns = _applicationResults.get(appName).get(cbxEvent.getSelectedItem()).GetTotalRuns();
				double averageResult = _applicationResults.get(appName).get(cbxEvent.getSelectedItem()).GetAverageResult();
				return new Object[]{appName, averageResult, noOfRuns};
			}
		}
		
		return new Object[]{appName, 0, 0};
	}

	private void RunExternalCommnad(String command)
    {
    	try
    	{
			Runtime r = Runtime.getRuntime();
		    Process p;     		// Process tracks one external native process
		    BufferedReader is;  // reader for output of process
		    String line;
		
		    System.out.println("Executing \"" + command + "\" command...");
		    
		    p = r.exec(command);
		
		    is = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
			while ((line = is.readLine()) != null)
			  System.out.println(line);
		    
		    System.out.println("End of command output. Waiting for exit...");
		    System.out.flush();

		    p.waitFor();  // wait for process to complete

		    System.out.println("Process done, exit status was " + p.exitValue());
		
		}
    	catch (IOException e1)
    	{
			e1.printStackTrace();
		}
    	catch (InterruptedException e2)
    	{
			e2.printStackTrace();
		}
    	
	    return;
    }
}
