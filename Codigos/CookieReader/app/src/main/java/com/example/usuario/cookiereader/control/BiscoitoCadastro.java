package com.example.usuario.cookiereader.control;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.cookiereader.DAO.BiscoitoDAO;
import com.example.usuario.cookiereader.DAO.BiscoitoNutrienteDAO;
import com.example.usuario.cookiereader.DAO.EmpresaDAO;
import com.example.usuario.cookiereader.DAO.NutrienteDAO;
import com.example.usuario.cookiereader.R;
import com.example.usuario.cookiereader.database.DataBase;
import com.example.usuario.cookiereader.domain.Biscoito;
import com.example.usuario.cookiereader.domain.BiscoitoNutriente;
import com.example.usuario.cookiereader.domain.Empresa;
import com.example.usuario.cookiereader.domain.Nutrientes;

import java.util.ArrayList;
import java.util.List;

public class BiscoitoCadastro extends AppCompatActivity {

    private BiscoitoDAO biscoitoDAO;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Biscoito biscoito;

    private Button btnInserir;
    private Button btnVer;
    private Button btnDeletar;
    private Button btnAlterar;

    private EditText editNome;
    private EditText editBarras;
    private TextView editCd;

    private Button btn_back;
    private ListView listBiscoito;

    private Spinner spinEmpresa;
    List<Empresa> empresa;

    List<Nutrientes> nutrientes;
    NutrienteDAO nutrienteDAO;
    Nutrientes nutriente;

    List<BiscoitoNutriente> biscoitoNutrientes;
    BiscoitoNutriente biscoitoNutriente;
    BiscoitoNutrienteDAO biscoitoNutrienteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biscoito_cadastro);

        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();

        biscoitoDAO = new BiscoitoDAO(conn);
        nutrienteDAO =  new NutrienteDAO(conn);
        biscoitoNutrienteDAO = new BiscoitoNutrienteDAO(conn);

        btnAlterar = (Button) findViewById(R.id.btnAlterar);
        btnVer = (Button) findViewById(R.id.btnVer);
        btnDeletar = (Button) findViewById(R.id.btnDeletar);
        btnInserir = (Button) findViewById(R.id.btnInserir);
        btn_back = (Button) findViewById(R.id.bnt_back);

        editCd =(TextView) findViewById(R.id.editCd);
        editNome = (EditText) findViewById(R.id.editNome);
        editBarras = (EditText) findViewById(R.id.editBarras);

        nutrientes = nutrienteDAO.BuscarTodos();


        spinEmpresa = (Spinner) findViewById(R.id.spinEmpresa);
        carregarComboBox();

        listBiscoito = (ListView) findViewById(R.id.listBiscoito);
        listarBiscoitos();

        listBiscoito.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                biscoito = (Biscoito) listBiscoito.getItemAtPosition(position);
                biscoitoNutrientes = biscoitoNutrienteDAO.BuscarTodos(biscoito.getCdBiscoito());

            }
        });

    }

    public void listarBiscoitos(){
        List<Biscoito> biscoitos;
        biscoitos = biscoitoDAO.BuscarTodos();
        ArrayAdapter<Biscoito> arrayAdapter = new ArrayAdapter<Biscoito>(this, android.R.layout.simple_list_item_checked, biscoitos);
        listBiscoito.setAdapter(arrayAdapter);
    }

    public void carregarComboBox(){
        EmpresaDAO empresaDAO = new EmpresaDAO(conn);
        empresa = empresaDAO.BuscarTodos();
        ArrayAdapter<Empresa> arrayAdapter = new ArrayAdapter<Empresa>(this, android.R.layout.simple_spinner_item, empresa);
        spinEmpresa.setAdapter(arrayAdapter);
    }

    public void sair(View view){
        conn.close();
        finish();
    }

    public void descelecionar(View view){
        listBiscoito.setItemChecked(-1, true);
        biscoito = null;
        biscoitoNutrientes = null;
    }

    public void inserir(View view){
        Biscoito biscoito = new Biscoito();
        Empresa empresa;
        empresa = (Empresa) spinEmpresa.getSelectedItem();
        biscoito.setNome(editNome.getText().toString());
        biscoito.setCdEmpresa(empresa.getCdEmpresa());
        biscoito.setCdBarras(editBarras.getText().toString());

        biscoitoDAO.inserir(biscoito);

        biscoito = biscoitoDAO.buscarUltimo();

        for(int i= 0; i <= biscoitoNutrientes.size()-1; i++){
            BiscoitoNutriente aux = new BiscoitoNutriente();
            aux.setCdBiscoito(biscoito.getCdBiscoito());
            aux.setCdNutrientes(biscoitoNutrientes.get(i).getCdNutrientes());
            aux.setQuant(biscoitoNutrientes.get(i).getQuant());
            biscoitoNutrienteDAO.inserir(aux);
        }

        listarBiscoitos();
    }

    public void deletar(View view){
        biscoitoDAO.excluir(biscoito.getCdBiscoito());
        listarBiscoitos();
    }

    public void ver(View view){
        int posicao = 0;
        EmpresaDAO empresaDAO = new EmpresaDAO(conn);
        Empresa empresa;

        empresa = empresaDAO.buscar(biscoito.getCdEmpresa());
        editNome.setText(biscoito.getNome());
        editCd.setText(Integer.toString(biscoito.getCdBiscoito()));
        editBarras.setText(biscoito.getCdBarras());

        for(int i =0; i < this.empresa.size(); i++){
            if(this.empresa.get(i).getNome().equals(empresa.getNome())){
                posicao = i;
            }
        }

        spinEmpresa.setSelection(posicao);
    }

    public void alterar(View view){
        Empresa empresa;
        empresa = (Empresa) spinEmpresa.getSelectedItem();
        biscoito.setNome(editNome.getText().toString());
        biscoito.setCdEmpresa(empresa.getCdEmpresa());
        biscoito.setCdBarras(editBarras.getText().toString());
        biscoitoDAO.alterar(biscoito);

        biscoitoNutrienteDAO.excluir(biscoito.getCdBiscoito());

        inserirNutrientes();
        listarBiscoitos();
    }

    public  void inserirNutrientes(){
        int id = biscoito.getCdBiscoito();
        for(int i= 0; i <= biscoitoNutrientes.size()-1; i++){
            BiscoitoNutriente aux = new BiscoitoNutriente();
            aux.setCdBiscoito(id);
            aux.setCdNutrientes(biscoitoNutrientes.get(i).getCdNutrientes());
            aux.setQuant(biscoitoNutrientes.get(i).getQuant());
            biscoitoNutrienteDAO.inserir(aux);
        }
    }

    public void limpar(View view){
        editBarras.setText(null);
        editNome.setText(null);
        editCd.setText(null);
        spinEmpresa.setSelection(0);
    }

    public void carregarComboBoxNutriente(Spinner spinNutrientes){
        ArrayAdapter<Nutrientes> arrayAdapter = new ArrayAdapter<Nutrientes>(this, android.R.layout.simple_spinner_item, nutrientes);
        spinNutrientes.setAdapter(arrayAdapter);
    }

    public void listarNutriente(ListView listNutri){
        if(biscoitoNutrientes == null){
                biscoitoNutrientes = new ArrayList<>();
                ArrayAdapter<BiscoitoNutriente> arrayAdapter = new ArrayAdapter<BiscoitoNutriente>(this, android.R.layout.simple_list_item_checked, biscoitoNutrientes);
                listNutri.setAdapter(arrayAdapter);
        }else {
            ArrayAdapter<BiscoitoNutriente> arrayAdapter = new ArrayAdapter<BiscoitoNutriente>(this, android.R.layout.simple_list_item_checked, biscoitoNutrientes);
            listNutri.setAdapter(arrayAdapter);
        }
    }

    public void nutriente(View view){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(BiscoitoCadastro.this);
        View mView = getLayoutInflater().inflate(R.layout.inserir_nutriente, null);
        final EditText editQuant = (EditText) mView.findViewById(R.id.editPeso);
        final Spinner spinNutrientes = (Spinner) mView.findViewById(R.id.spinDcnt);
        final ListView listNutri = (ListView) mView.findViewById(R.id.listNutri);
        Button btnVoltar = (Button) mView.findViewById(R.id.btnVoltar);
        Button btnInserir = (Button) mView.findViewById(R.id.btnInserir);
        Button btnDelNutri = (Button) mView.findViewById(R.id.btnDel);

        carregarComboBoxNutriente(spinNutrientes);

        listarNutriente(listNutri);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        listNutri.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                biscoitoNutriente = (BiscoitoNutriente) listNutri.getItemAtPosition(position);
            }
        });

        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editQuant.getText().toString().isEmpty()){
                    BiscoitoNutriente nutrientes = new BiscoitoNutriente();
                    nutrientes.setQuant(Float.parseFloat(editQuant.getText().toString()));
                    nutriente = (Nutrientes) spinNutrientes.getSelectedItem();
                    nutrientes.setCdNutrientes(nutriente.getCdNutriente());
                    nutrientes.setNomeNutriente(nutriente.getNome());

                    boolean testeinsercao = false;
                    for (BiscoitoNutriente aux : biscoitoNutrientes){
                        if(aux.getNomeNutriente().equals(nutriente.getNome())){
                            testeinsercao = true;
                            Toast.makeText(BiscoitoCadastro.this, "Nutriente ja Inserido", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if (!testeinsercao){
                        biscoitoNutrientes.add(nutrientes);
                        listarNutriente(listNutri);
                    }
                }
            }
        });

        btnDelNutri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biscoitoNutrientes.remove(biscoitoNutriente);
                listarNutriente(listNutri);
            }
        });

    }

}
