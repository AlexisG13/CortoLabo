/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Consulta;

import filtro.Filtro;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import metFiltro.metFiltro;

/**
 *
 * @author LN710Q
 */
public class Consulta extends JFrame{
    public JLabel lblcarnet, lblnombre, lblapellidos, lbledad, lbluniversidad, lblestado;
    public JTextField carnet, nombre, apellidos, edad;
    public JComboBox universidad;
    
    ButtonGroup estado = new ButtonGroup();
    public JRadioButton si;
    public JRadioButton no;
    public JTable resultados;
    public JPanel table;
    public JButton buscar,eliminar,insertar,limpiar,actualizar;
    
    private static final int ANCHOC = 120, ALTOC =40;
    
    DefaultTableModel tm;
    
    public Consulta(){
        super("Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        agregarLabels();
        formulario();
        llenarTabla();
        Container container = getContentPane();
        container.add(lblcarnet);
        container.add(lblnombre);
        container.add(lblapellidos);
        container.add(lbledad);
        container.add(lbluniversidad);
        container.add(universidad);
        container.add(lblestado);
        container.add(carnet);
        container.add(nombre);
        container.add(apellidos);
        container.add(edad);
        container.add(si);
        container.add(no);
        container.add(buscar);
        container.add(insertar);
        container.add(actualizar);
        container.add(eliminar);
        container.add(limpiar);
        container.add(table);
        setSize(1000,1000);
        eventos();
    }
    
    public final void agregarLabels(){
        lblnombre = new JLabel("Nombre");
        lblapellidos = new JLabel("Apellidos");
        lbledad = new JLabel("Edad");
        lbluniversidad = new JLabel("Universidad");
        lblcarnet = new JLabel("Carnet");
        lblestado = new JLabel("Estado");
        lblcarnet.setBounds(10, 10, ANCHOC, ALTOC);
        lblnombre.setBounds(10, 60, ANCHOC, ALTOC);
        lblapellidos.setBounds(300, 60, ANCHOC, ALTOC);
        lbledad.setBounds(10, 110, ANCHOC, ALTOC);
        lbluniversidad.setBounds(10,160,ANCHOC,ALTOC);
        lblestado.setBounds(10,210,ANCHOC,ALTOC);
    }
    
    public final void formulario(){
        nombre = new JTextField();
        universidad = new JComboBox();
        apellidos = new JTextField();
        carnet = new JTextField();
        edad = new JTextField();
        si = new JRadioButton("si",true);
        no = new JRadioButton("no");
        resultados = new JTable();
        buscar = new JButton("Buscar");
        insertar = new JButton("Insertar");
        eliminar = new JButton("Eliminar");
        actualizar = new JButton("Actualizar");
        limpiar = new JButton("Limpiar");
        table = new JPanel();
        universidad.addItem("UCA");
        universidad.addItem("U. NACIONAL");
        universidad.addItem("UDB");
        universidad.addItem("UEES");
        estado = new ButtonGroup();
        estado.add(si);
        estado.add(no);
        carnet.setBounds(140,10,ANCHOC,ALTOC);
        nombre.setBounds(140, 60, ANCHOC, ALTOC);
        apellidos.setBounds(400, 60, ANCHOC, ALTOC);
        universidad.setBounds(200,160,ANCHOC,ALTOC);
        edad.setBounds(140, 110, ANCHOC, ALTOC);
        si.setBounds(140, 210, 50, ALTOC);
        no.setBounds(240, 210, 50, ALTOC);

        buscar.setBounds(300, 10, ANCHOC, ALTOC);
        insertar.setBounds(10, 300, ANCHOC, ALTOC);
        actualizar.setBounds(150, 300, ANCHOC, ALTOC);
        eliminar.setBounds(300, 300, ANCHOC, ALTOC);
        limpiar.setBounds(450, 300, ANCHOC, ALTOC);
        resultados = new JTable();
        table.setBounds(10,400,500,200);
        table.add(new JScrollPane(resultados));
    }
    
    public void llenarTabla(){
        tm = new DefaultTableModel() {
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        
        tm.addColumn("Carnet");
        tm.addColumn("Nombre");
        tm.addColumn("Apellidos");
        tm.addColumn("Edad");
        tm.addColumn("Universidad");
        tm.addColumn("Estado");
        metFiltro fd= new metFiltro();
        ArrayList<Filtro> filtros = fd.readAll();
        
        for(Filtro fi : filtros){
            tm.addRow(new Object[]{fi.getCarnet(),fi.getNombre(),fi.getApellidos(),fi.getUniversidad(),fi.getEdad(),fi.isEstado()});
        }
        
        resultados.setModel(tm);
    }
    
    public void eventos(){
        
        insertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metFiltro fd = new metFiltro();
                Filtro f = new Filtro(carnet.getText(),nombre.getText(),apellidos.getText(),universidad.getSelectedItem().toString(),Integer.parseInt(edad.getText()),true);
                
                if(no.isSelected()){
                    f.setEstado(false);
                }
                if(fd.create(f)){
                    JOptionPane.showMessageDialog(null,"Filtro registrado con exito.");
                    limpiarCampos();
                    llenarTabla();
                } else{
                    JOptionPane.showMessageDialog(null, "Ocurrió un problema al momento de modificar el filtro.");
                    JOptionPane.showMessageDialog(null, "Asegurar que el carnet es de menos de 9 dígitos");
                    
                }
            }
        });
        
        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metFiltro fd = new metFiltro();
                if(fd.delete(carnet.getText())){
                    JOptionPane.showMessageDialog(null, "Filtro Eliminado con éxito.");
                    limpiarCampos();
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrió un problema al momento de eliminar el filtro.");
                    JOptionPane.showMessageDialog(null, "Asegurar que el carnet es de menos de 9 digitos ");
                }
            }
        });
        
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metFiltro fd = new metFiltro();
                Filtro f = fd.read(carnet.getText());
                if(f==null){
                    JOptionPane.showMessageDialog(null, "El filtro buscado no se ha encontrado.");
                    JOptionPane.showMessageDialog(null, "Asegurar que el carnet sea de menos de 9 digitos");
                } else{
                    
                    carnet.setText(f.getCarnet());
                    nombre.setText(f.getNombre());
                    apellidos.setText(f.getApellidos());
                    edad.setText(Integer.toString(f.getEdad()));
                    universidad.setSelectedItem(f.getUniversidad());
                    
                    if(f.isEstado()){
                        si.setSelected(true);
                    } else{
                        no.setSelected(true);
                    }
                }
            }
        });
        
        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }
    
    public void limpiarCampos(){
        nombre.setText("");
        universidad.setSelectedItem("UCA");
        apellidos.setText("");
        edad.setText("");
        carnet.setText("");
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Consulta().setVisible(true);
            }
        });
    }
    
}
