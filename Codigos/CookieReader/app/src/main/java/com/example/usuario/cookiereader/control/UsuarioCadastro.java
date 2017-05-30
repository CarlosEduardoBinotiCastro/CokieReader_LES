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
import android.widget.TextView;

import com.example.usuario.cookiereader.DAO.CidadeDAO;
import com.example.usuario.cookiereader.DAO.UfDAO;
import com.example.usuario.cookiereader.DAO.UsuarioDAO;
import com.example.usuario.cookiereader.Misc.Md5;
import com.example.usuario.cookiereader.R;
import com.example.usuario.cookiereader.database.DataBase;
import com.example.usuario.cookiereader.domain.Cidade;
import com.example.usuario.cookiereader.domain.UF;
import com.example.usuario.cookiereader.domain.Usuario;

import java.util.List;

public class UsuarioCadastro extends AppCompatActivity {

    private UsuarioDAO usuarioDAO;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Usuario usuario;

    private Button btnInserir;
    private Button btnVer;
    private Button btnDeletar;
    private Button btnAlterar;

    private EditText editNome;
    private EditText editLogin;
    private EditText editCpf;
    private EditText editSenha;
    private TextView editCd;

    private Button btn_back;
    private ListView listUsuario;

    private Spinner spinUf;
    private Spinner spinCidade;
    List<UF> ufs;
    List<Cidade> cidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_cadastro);
        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        usuarioDAO = new UsuarioDAO(conn);

        btnAlterar = (Button) findViewById(R.id.btnAlterar);
        btnVer = (Button) findViewById(R.id.btnVer);
        btnDeletar = (Button) findViewById(R.id.btnDeletar);
        btnInserir = (Button) findViewById(R.id.btnInserir);
        btn_back = (Button) findViewById(R.id.bnt_back);

        editCd =(TextView) findViewById(R.id.editCd);
        editNome = (EditText) findViewById(R.id.editNome);
        editLogin = (EditText) findViewById(R.id.editLogin);
        editCpf = (EditText) findViewById(R.id.editCpf);
        editSenha = (EditText) findViewById(R.id.editSenha);

        spinUf = (Spinner) findViewById(R.id.spinUf);
        carregarComboBoxUf();
        spinCidade = (Spinner) findViewById(R.id.spinCidade);
        carregarComboBoxCidade();

        listUsuario = (ListView) findViewById(R.id.listUsuario);
        listarUsuario();

        listUsuario.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                usuario = (Usuario) listUsuario.getItemAtPosition(position);
            }
        });

        spinUf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                carregarComboBoxCidade();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    public void listarUsuario(){
        List<Usuario> usuario;
        usuario = usuarioDAO.BuscarTodos();
        ArrayAdapter<Usuario> arrayAdapter = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_checked, usuario);
        listUsuario.setAdapter(arrayAdapter);
    }

    public void carregarComboBoxCidade(){
        UF uf = (UF) spinUf.getSelectedItem();
        CidadeDAO cidadeDAO = new CidadeDAO(conn);
        cidades = cidadeDAO.BuscarTodosPorUF(uf.getSigla());
        ArrayAdapter<Cidade> arrayAdapter = new ArrayAdapter<Cidade>(this, android.R.layout.simple_spinner_item, cidades);
        spinCidade.setAdapter(arrayAdapter);
    }

    public void carregarComboBoxUf(){
        UfDAO ufDAO = new UfDAO(conn);
        ufs = ufDAO.BuscarTodos();
        ArrayAdapter<UF> arrayAdapter = new ArrayAdapter<UF>(this, android.R.layout.simple_spinner_item, ufs);
        spinUf.setAdapter(arrayAdapter);
    }

    public void sair(View view){
        conn.close();
        finish();
    }

    public void inserir(View view){
        Usuario usuario = new Usuario();
        Cidade cidade;
        Md5 md5 = new Md5();
        String senha;
        cidade = (Cidade) spinCidade.getSelectedItem();

        usuario.setNome(editNome.getText().toString());
        usuario.setLogin(editLogin.getText().toString());
        usuario.setCpf(editCpf.getText().toString());

        senha = md5.gerarMd5(editSenha.getText().toString());
        usuario.setSenha(senha);
        usuario.setCdCidade(cidade.getCdCidade());

        usuarioDAO.inserir(usuario);
        listarUsuario();
    }

    public void deletar(View view){
        usuarioDAO.excluir(usuario.getCdUsuario());
        listarUsuario();
    }

    public void alterar(View view){
        Cidade cidade;
        cidade = (Cidade) spinCidade.getSelectedItem();
        Md5 md5 = new Md5();
        String senha;

        usuario.setNome(editNome.getText().toString());
        usuario.setCdCidade(cidade.getCdCidade());
        usuario.setCpf(editCpf.getText().toString());
        usuario.setLogin(editLogin.getText().toString());

        senha = md5.gerarMd5(editSenha.getText().toString());
        usuario.setSenha(senha);

        usuarioDAO.alterar(usuario);
        listarUsuario();
    }

    public void limpar(View view){
        editNome.setText(null);
        editCd.setText(null);
        editLogin.setText(null);
        editCpf.setText(null);
        editSenha.setText(null);
        spinUf.setSelection(0);
    }

    public void ver(View view){
        int posicao = 0;
        CidadeDAO cidadeDAO = new CidadeDAO(conn);
        Cidade cidade = cidadeDAO.buscar(usuario.getCdCidade());

        UfDAO ufDAO = new UfDAO(conn);
        UF uf;
        uf = ufDAO.buscar(cidade.getSiglaUf());
        editNome.setText(usuario.getNome());
        editCd.setText(Integer.toString(usuario.getCdUsuario()));
        editSenha.setText(null);
        editLogin.setText(usuario.getLogin());
        editCpf.setText(usuario.getCpf());
        for(int i =0; i < ufs.size(); i++){
            if(ufs.get(i).getNome().equals(uf.getNome())){
                posicao = i;
            }
        }
        spinUf.setSelection(posicao);
        carregarComboBoxCidade();
        for (int i = 0; i < cidades.size(); i++){
            if(cidades.get(i).getCdCidade()== cidade.getCdCidade()){
                posicao = i;
            }

        }
        spinCidade.setSelection(posicao);
    }

}
