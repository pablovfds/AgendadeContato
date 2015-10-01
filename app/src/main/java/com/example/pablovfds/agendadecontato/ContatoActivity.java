package com.example.pablovfds.agendadecontato;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.pablovfds.agendadecontato.database.DataBase;
import com.example.pablovfds.agendadecontato.dominio.RepositorioContato;
import com.example.pablovfds.agendadecontato.dominio.entidades.Contato;

public class ContatoActivity extends Activity implements View.OnClickListener {

    private ImageButton imageButtonAdicionar;
    private ListView listViewContatos;
    private EditText editTextPesquisa;
    private DataBase dataBase;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayAdapter<Contato> arrayAdapterContatos;
    private RepositorioContato repositorioContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        imageButtonAdicionar = (ImageButton) findViewById(R.id.imageButtonAdicionar);
        editTextPesquisa = (EditText) findViewById(R.id.editTextPesquisa);
        listViewContatos = (ListView) findViewById(R.id.listViewContatos);

        imageButtonAdicionar.setOnClickListener(this);
        try {
            dataBase = new DataBase(this);
            sqLiteDatabase = dataBase.getWritableDatabase();
            repositorioContato = new RepositorioContato(sqLiteDatabase);

            arrayAdapterContatos = repositorioContato.buscaContatos(this);
            listViewContatos.setAdapter(arrayAdapterContatos);

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar o Banco de Dados: " + ex.getMessage());
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CadastroContatosActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        arrayAdapterContatos = repositorioContato.buscaContatos(this);
        listViewContatos.setAdapter(arrayAdapterContatos);
    }
}