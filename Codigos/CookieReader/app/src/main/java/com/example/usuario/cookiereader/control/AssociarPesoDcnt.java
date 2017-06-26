package com.example.usuario.cookiereader.control;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.usuario.cookiereader.DAO.BiscoitoDAO;
import com.example.usuario.cookiereader.DAO.DcntDAO;
import com.example.usuario.cookiereader.DAO.DcntPesoDAO;
import com.example.usuario.cookiereader.DAO.NutrienteDAO;
import com.example.usuario.cookiereader.R;
import com.example.usuario.cookiereader.database.DataBase;
import com.example.usuario.cookiereader.domain.DCNTpeso;
import com.example.usuario.cookiereader.domain.Nutrientes;
import com.example.usuario.cookiereader.domain.dcnt;

import java.util.ArrayList;
import java.util.List;

public class AssociarPesoDcnt extends AppCompatActivity {

    private DcntPesoDAO dcntPesoDAO;
    private DataBase dataBase;
    private SQLiteDatabase conn;

    private ListView listPeso;

    private Button btnInserir;
    private Button btnDeletar;
    private Button btnVoltar;

    private Spinner spinNutriente;
    private Spinner spinDcnt;

    private EditText editPeso;

    List<com.example.usuario.cookiereader.domain.dcnt> dcnts;
    List<Nutrientes> nutrientes;
    DCNTpeso nutrientePeso;
    List<DCNTpeso> dcnTpesos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associar_peso_dcnt);

        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        dcntPesoDAO = new DcntPesoDAO(conn);

        btnDeletar = (Button) findViewById(R.id.btnDeletar);
        btnInserir = (Button) findViewById(R.id.btnInserir);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        spinDcnt = (Spinner) findViewById(R.id.spinDcnt);
        spinNutriente = (Spinner) findViewById(R.id.spinNutriente);

        editPeso = (EditText) findViewById(R.id.editPeso);

        listPeso = (ListView) findViewById(R.id.listPeso);

        carregarComboBoxDcnt();
        listarDcntPeso();
        verificarComboBox();

        listPeso.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                nutrientePeso = (DCNTpeso) listPeso.getItemAtPosition(position);
            }
        });

        spinDcnt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                listarDcntPeso();
                verificarComboBox();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    public void listarDcntPeso(){
        com.example.usuario.cookiereader.domain.dcnt dcnt = (com.example.usuario.cookiereader.domain.dcnt) spinDcnt.getSelectedItem();
        dcnTpesos = dcntPesoDAO.BuscarTodos(dcnt.getCdDcnt());
        ArrayAdapter<DCNTpeso> arrayAdapter = new ArrayAdapter<DCNTpeso>(this, android.R.layout.simple_list_item_checked, dcnTpesos);
        listPeso.setAdapter(arrayAdapter);
    }

    public void carregarComboBoxDcnt(){
        DcntDAO dcntDAO = new DcntDAO(conn);
        dcnts = dcntDAO.BuscarTodos();
        ArrayAdapter<com.example.usuario.cookiereader.domain.dcnt> arrayAdapter = new ArrayAdapter<com.example.usuario.cookiereader.domain.dcnt>(this, android.R.layout.simple_spinner_item, dcnts);
        spinDcnt.setAdapter(arrayAdapter);
    }

    public void carregarComboBoxNutriente(){
        ArrayAdapter<Nutrientes> arrayAdapter = new ArrayAdapter<Nutrientes>(this, android.R.layout.simple_spinner_item, nutrientes);
        spinNutriente.setAdapter(arrayAdapter);
    }

    public void verificarComboBox(){
        NutrienteDAO nutrienteDAO = new NutrienteDAO(conn);
        nutrientes = nutrienteDAO.BuscarTodos();
        ArrayList<Nutrientes> arrayNutri = new ArrayList<>();
        if(!dcnTpesos.isEmpty()) {
            for (Nutrientes auxNutri : nutrientes) {
                for (DCNTpeso aux : dcnTpesos) {
                    if (aux.getCdNutriente() == auxNutri.getCdNutriente()) {
                        arrayNutri.add(auxNutri);
                        break;
                    }
                }
            }
            for (Nutrientes auxNutri : arrayNutri){
                nutrientes.remove(auxNutri);
            }
            carregarComboBoxNutriente();
        }else{
            carregarComboBoxNutriente();
        }

    }


    public void inserir(View view){
        DCNTpeso pesoAux = new DCNTpeso();
        com.example.usuario.cookiereader.domain.dcnt dcnt = (com.example.usuario.cookiereader.domain.dcnt) spinDcnt.getSelectedItem();
        Nutrientes nutriente = (Nutrientes) spinNutriente.getSelectedItem();

        pesoAux.setPeso(Integer.parseInt(editPeso.getText().toString()));
        pesoAux.setCdDcnt(dcnt.getCdDcnt());
        pesoAux.setCdNutriente(nutriente.getCdNutriente());

        dcntPesoDAO.inserir(pesoAux);
        listarDcntPeso();
        verificarComboBox();
    }

    public void deletar(View view){
        com.example.usuario.cookiereader.domain.dcnt dcnt = (com.example.usuario.cookiereader.domain.dcnt) spinDcnt.getSelectedItem();

        dcntPesoDAO.excluir(dcnt.getCdDcnt(), nutrientePeso.getCdNutriente());

        listarDcntPeso();
        verificarComboBox();
    }

    public void voltar(View view){
        finish();
    }
}
