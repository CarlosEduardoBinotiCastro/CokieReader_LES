package com.example.usuario.cookiereader.control;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.cookiereader.DAO.BiscoitoDAO;
import com.example.usuario.cookiereader.DAO.BiscoitoNutrienteDAO;
import com.example.usuario.cookiereader.DAO.DcntDAO;
import com.example.usuario.cookiereader.DAO.DcntPesoDAO;
import com.example.usuario.cookiereader.DAO.EmpresaDAO;
import com.example.usuario.cookiereader.DAO.ScanDAO;
import com.example.usuario.cookiereader.DAO.UsuarioDAO;
import com.example.usuario.cookiereader.Misc.Sessao;
import com.example.usuario.cookiereader.R;
import com.example.usuario.cookiereader.database.DataBase;
import com.example.usuario.cookiereader.domain.Biscoito;
import com.example.usuario.cookiereader.domain.BiscoitoNutriente;
import com.example.usuario.cookiereader.domain.DCNTpeso;
import com.example.usuario.cookiereader.domain.Empresa;
import com.example.usuario.cookiereader.domain.Nutrientes;
import com.example.usuario.cookiereader.domain.Scan;
import com.example.usuario.cookiereader.domain.Usuario;
import com.example.usuario.cookiereader.domain.dcnt;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Escaneamento extends AppCompatActivity {

    private List<com.example.usuario.cookiereader.domain.dcnt> dcnts;

    private String barras;
    private DataBase dataBase;
    final Activity activity = this;
    private SQLiteDatabase conn;

    private TextView textNome;
    private TextView textEmpresa;
    private TextView textSugestao;

    private Spinner spinDoenca;
    private ListView listNutri;

    com.example.usuario.cookiereader.domain.dcnt dcnt;
    private Biscoito biscoito;

    List<BiscoitoNutriente> biscoitoNutrientes;
    List<DCNTpeso> pesos;
    DcntPesoDAO pesoDAO;
    Sessao sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaneamento);

        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        sessao = new Sessao();

        textNome =(TextView) findViewById(R.id.nomeBiscoito);
        textEmpresa =(TextView) findViewById(R.id.empresa);
        textSugestao =(TextView) findViewById(R.id.sugestao);
        listNutri = (ListView) findViewById(R.id.listNutri);
        pesoDAO = new DcntPesoDAO(conn);

        spinDoenca = (Spinner) findViewById(R.id.spinDoenca);
        carregarComboBox();

        spinDoenca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                dcnt = (com.example.usuario.cookiereader.domain.dcnt) spinDoenca.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
    public void voltar (View view){
        finish();
    }

    public void carregarComboBox(){
        DcntDAO dcntDAO = new DcntDAO(conn);
        dcnts = dcntDAO.BuscarTodos();
        ArrayAdapter<com.example.usuario.cookiereader.domain.dcnt> arrayAdapter = new ArrayAdapter<com.example.usuario.cookiereader.domain.dcnt>(this, android.R.layout.simple_spinner_item, dcnts);
        spinDoenca.setAdapter(arrayAdapter);
    }

    public void scan(View view){
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                barras = result.getContents();
                buscarBiscoitoDoenca();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void buscarBiscoitoDoenca(){
        BiscoitoDAO biscoitoDAO = new BiscoitoDAO(conn);
        biscoito = biscoitoDAO.buscarPorBarras(barras);
        UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
        Usuario user = usuarioDAO.buscar(sessao.getUsuario().getCdUsuario());
        int quantEscaneamento = user.getQuantEscaneamento();
        if(quantEscaneamento > 0) {
            if (biscoito != null) {
                BiscoitoNutrienteDAO biscoitoNutrienteDAO = new BiscoitoNutrienteDAO(conn);
                biscoitoNutrientes = biscoitoNutrienteDAO.BuscarTodos(biscoito.getCdBiscoito());
                EmpresaDAO empresaDAO = new EmpresaDAO(conn);
                Empresa empresa = empresaDAO.buscar(biscoito.getCdEmpresa());
                ArrayAdapter<BiscoitoNutriente> arrayAdapter = new ArrayAdapter<BiscoitoNutriente>(this, android.R.layout.simple_list_item_checked, biscoitoNutrientes);
                listNutri.setAdapter(arrayAdapter);

                textNome.setText(biscoito.getNome());
                textEmpresa.setText(empresa.getNome());

                pesos = pesoDAO.BuscarTodos(dcnt.getCdDcnt());
                usuarioDAO.atualizarQuantEscaneamento(quantEscaneamento-1, sessao.getUsuario().getCdUsuario());
                meSugira();

            } else {
                Toast.makeText(Escaneamento.this, "Biscoito não encontrado", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(Escaneamento.this, "Limite do dia Excedido", Toast.LENGTH_SHORT).show();
        }
    }

    public void meSugira() {
        ArrayList<DCNTpeso> listaPeso = new ArrayList<>();
        float sugerir = 0;
        float piorNutri = 0;
        ArrayList<BiscoitoNutriente> quantAux = new ArrayList<>();
        for (DCNTpeso pesoAux : pesos) {
            for (BiscoitoNutriente nutrienteAux : biscoitoNutrientes) {
                if (pesoAux.getCdNutriente() == nutrienteAux.getCdNutrientes()) {
                    listaPeso.add(pesoAux);
                    sugerir += nutrienteAux.getQuant();
                    quantAux.add(nutrienteAux);
                    break;
                }
            }
        }
        Collections.sort(listaPeso);

        for (DCNTpeso pesoAux : listaPeso) {
            for (BiscoitoNutriente nutrienteAux : quantAux) {
                if (pesoAux.getCdNutriente() == nutrienteAux.getCdNutrientes()) {
                    piorNutri = nutrienteAux.getQuant();
                    break;
                }
            }
            break;
        }

        sugerir = piorNutri / sugerir * 100;

        if (sugerir > 30) {
            textSugestao.setText("Evite ao maximo");
        } else {
            if (sugerir > 20) {
                textSugestao.setText("Tente evitar");
            } else {
                textSugestao.setText("Consuma com Moderacao");
            }

        }


        ScanDAO scanDAO = new ScanDAO(conn);
        Scan scan = new Scan();

        scan.setCdUsuario(sessao.getUsuario().getCdUsuario());
        scan.setCdBiscoito(biscoito.getCdBiscoito());
        scan.setCdDcnt(dcnt.getCdDcnt());

        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setTimeZone(cal.getTimeZone());
        scan.setData(dateFormat.format(cal.getTime()));

        scanDAO.inserir(scan);

    }



}
