package com.example.usuario.cookiereader;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.usuario.cookiereader.DAO.UsuarioDAO;
import com.example.usuario.cookiereader.Misc.FirstLogin;
import com.example.usuario.cookiereader.Misc.Md5;
import com.example.usuario.cookiereader.Misc.Sessao;
import com.example.usuario.cookiereader.control.Home;
import com.example.usuario.cookiereader.database.DataBase;
import com.example.usuario.cookiereader.domain.Usuario;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Main extends AppCompatActivity {

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Button btnEntrar;
    private EditText textLogin;
    private EditText textSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        textLogin = (EditText) findViewById(R.id.textLogin);
        textSenha = (EditText) findViewById(R.id.textSenha);

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getReadableDatabase();

            FirstLogin firstLogin = new FirstLogin(this, conn);
            firstLogin.firstLogin();

        } catch (SQLException ex){}

    }

    public void sair(View view){
        conn.close();
        finish();
    }

    public void entrar(View view){
        Usuario user = new Usuario();
        Cursor cursor;
        try{
            String login = textLogin.getText().toString();
            String senha = textSenha.getText().toString();
            Md5 md5 = new Md5();
            cursor = conn.rawQuery("SELECT * from Usuario WHERE login = '"+login+"'", null);
            if(cursor.moveToFirst()){
                user.setCdUsuario(cursor.getInt(cursor.getColumnIndex("_cdUsuario")));
                user.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                user.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                user.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
                user.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
                user.setDataSessao(cursor.getString(cursor.getColumnIndex("dataSessao")));

                senha = md5.gerarMd5(senha);

                if (senha.equals(user.getSenha())){
                    Sessao sessao = new Sessao();
                    UsuarioDAO usuarioDAO = new UsuarioDAO(conn);

                    Date data = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(data);

                    if(user.getDataSessao() != null){
                        Calendar calendar = new GregorianCalendar();
                        SimpleDateFormat oldDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        Date dt = oldDate.parse(user.getDataSessao());
                        calendar.setTime(dt);

                        if(cal.after(calendar)){
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            dateFormat.setTimeZone(cal.getTimeZone());
                            user.setDataSessao(dateFormat.format(cal.getTime()));
                            usuarioDAO.alterarData(user);
                            sessao.setQuantScan(10);
                        }else{

                        }
                    }else{
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        dateFormat.setTimeZone(cal.getTimeZone());
                        user.setDataSessao(dateFormat.format(cal.getTime()));
                        usuarioDAO.alterarData(user);
                        sessao.setQuantScan(10);
                    }


                    sessao.logado(user);

                    Intent home = new Intent(this, Home.class);
                    startActivity(home);


                }else{
                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setTitle("Encontrado");
                    dlg.setMessage("Usuario Encontrado, mas senha inconfere");
                    dlg.setCancelable(true);
                    dlg.setPositiveButton("OK", null);
                    dlg.create();
                    dlg.show();
                }
            }else{
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("Não encontrado");
                dlg.setMessage("Usuario nao cadastrado");
                dlg.setCancelable(true);
                dlg.setPositiveButton("OK", null);
                dlg.create();
                dlg.show();
            }

        }catch (Exception ex){
            conn.close();

        }

    }


}
