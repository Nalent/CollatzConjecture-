import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

public class CollatzConjecture extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField maxNField;
    private JButton generateButton;

    public CollatzConjecture() {
        setTitle("Collatz Conjecture");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        maxNField = new JTextField(5);
        generateButton = new JButton("Generate");

        inputPanel.add(new JLabel("Max n(i):"));
        inputPanel.add(maxNField);
        inputPanel.add(generateButton);

        add(inputPanel, BorderLayout.NORTH);
		
		JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
		
		JPanel explanationPanel = new JPanel();
        explanationPanel.setLayout(new BoxLayout(explanationPanel, BoxLayout.Y_AXIS));
        explanationPanel.add(new JLabel("n(i) - the starting number"));
        explanationPanel.add(new JLabel("M(i) - max number in the cycle"));
        explanationPanel.add(new JLabel("IM - number of iterations to max number"));
        explanationPanel.add(new JLabel("N(i) - number of elements in the cycle"));
        explanationPanel.add(new JLabel("I - iterations"));
        mainPanel.add(explanationPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("n(i)");
        tableModel.addColumn("M(i)");
		tableModel.addColumn("IM");
        tableModel.addColumn("N(i)");
        tableModel.addColumn("I");
		tableModel.addColumn("Cycle");

        table = new JTable(tableModel) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                return new MultiLineCellRenderer();
            }
        };
        table.setFillsViewportHeight(true);

        table.getColumnModel().getColumn(0).setPreferredWidth(20);  
        table.getColumnModel().getColumn(1).setPreferredWidth(20); 
		table.getColumnModel().getColumn(2).setPreferredWidth(20);  
        table.getColumnModel().getColumn(3).setPreferredWidth(20);  
        table.getColumnModel().getColumn(4).setPreferredWidth(20); 
		table.getColumnModel().getColumn(5).setPreferredWidth(400); 

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
		
		add(mainPanel, BorderLayout.CENTER);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateTable();
            }
        });
    }

    private void generateTable() {
        String maxNText = maxNField.getText();
        try {
            int maxN = Integer.parseInt(maxNText);
			if(maxN <= 0){
				throw new NumberFormatException();
			}
            tableModel.setRowCount(0);
            for (int i = 1; i <= maxN; i++) {
                ArrayList<Integer> cycle = collatzCycle(i);
                int maxInCycle = cycle.stream().max(Integer::compare).orElse(0);
                int cycleLength = cycle.size();
                int iterations = cycleLength - 1;
				int maxMIterations = findIterationsToMaxM(cycle, maxInCycle);
                tableModel.addRow(new Object[]{i, maxInCycle, maxMIterations, cycleLength, iterations, cycle.toString()});
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static ArrayList<Integer> collatzCycle(int n) {
        ArrayList<Integer> cycle = new ArrayList<>();
        HashSet<Integer> visited = new HashSet<>();
        while (!visited.contains(n)) {
            visited.add(n);
            cycle.add(n);
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
        }
        return cycle;
    }
	
	private int findIterationsToMaxM(ArrayList<Integer> cycle, int maxInCycle) {
		int iterations = -1;
		for (int number : cycle) {
			iterations++;
			if (number == maxInCycle) {
				break;
			}
		}
		
		return iterations;
	}
   
}
