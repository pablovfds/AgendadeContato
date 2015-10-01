package com.example.pablovfds.agendadecontato;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pablovfds.agendadecontato.database.DataBase;
import com.example.pablovfds.agendadecontato.dominio.RepositorioContato;
import com.example.pablovfds.agendadecontato.dominio.entidades.Contato;
import com.example.pablovfds.agendadecontato.util.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class CadastroContatosActivity extends AppCompatActivity {

    private EditText editTextNome, editTextEmail, editTextTelefone,
            editTextEndereco, editTextDatasEspecias, editTextGrupos;

    private Spinner spinnerEmail, spinnerTelefone, spinnerDatasEspecias, spinnerEndereco;

    private ArrayAdapter<String> adapterTipoEmail, adapterTipoTelefone, adapterTipoEndereco,
            adapterTipoDatasEspeciais;

    private DataBase dataBase;
    private SQLiteDatabase sqLiteDatabase;
    private RepositorioContato repositorioContato;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contatos);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);
        editTextEndereco = (EditText) findViewById(R.id.editTextEndereco);
        editTextDatasEspecias = (EditText) findViewById(R.id.editTextDatasEspeciais);
        editTextGrupos = (EditText) findViewById(R.id.editTextGrupos);

        spinnerTelefone = (Spinner) findViewById(R.id.spinnerTipoTelefone);
        spinnerEmail = (Spinner) findViewById(R.id.spinnerTipoEmail);
        spinnerEndereco = (Spinner) findViewById(R.id.spinnerTipoEndereco);
        spinnerDatasEspecias = (Spinner) findViewById(R.id.spinnerTiposDatasEspeciais);

        adapterTipoEmail = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapterTipoEmail.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterTipoTelefone = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapterTipoTelefone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterTipoEndereco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapterTipoEndereco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterTipoDatasEspeciais = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapterTipoDatasEspeciais.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTelefone.setAdapter(adapterTipoTelefone);
        spinnerEndereco.setAdapter(adapterTipoEndereco);
        spinnerEmail.setAdapter(adapterTipoEmail);
        spinnerDatasEspecias.setAdapter(adapterTipoDatasEspeciais);

        adapterTipoEmail.add("Casa");
        adapterTipoEmail.add("Trabalho");
        adapterTipoEmail.add("Outros");

        adapterTipoTelefone.add("Celular");
        adapterTipoTelefone.add("Trabalho");
        adapterTipoTelefone.add("Casa");
        adapterTipoTelefone.add("Principal");
        adapterTipoTelefone.add("Fax Trabalho");
        adapterTipoTelefone.add("Fax Casa");
        adapterTipoTelefone.add("Pager");
        adapterTipoTelefone.add("Outros");

        adapterTipoEndereco.add("Casa");
        adapterTipoEndereco.add("Trabalho");
        adapterTipoEndereco.add("Outros");

        adapterTipoDatasEspeciais.add("Aniversario");
        adapterTipoDatasEspeciais.add("Data comemorativa");
        adapterTipoDatasEspeciais.add("Outros");

        ExibeDataListener exibeDataListener = new ExibeDataListener();

        editTextDatasEspecias.setOnClickListener(exibeDataListener);
        editTextDatasEspecias.setOnFocusChangeListener(exibeDataListener);

        contato = new Contato();

        try {
            dataBase = new DataBase(this);
            sqLiteDatabase = dataBase.getWritableDatabase();
            repositorioContato = new RepositorioContato(sqLiteDatabase);

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar o Banco de Dados: " + ex.getMessage());
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_cadastro_contatos, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_acao1:
                if (contato == null){
                    inserirContato();
                }
                finish();
                break;

            case R.id.menu_acao2:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void inserirContato(){
        try {

            contato.setNome(editTextNome.getText().toString());
            contato.setTelefone(editTextTelefone.getText().toString());
            contato.setEndereco(editTextEndereco.getText().toString());
            contato.setGrupos(editTextGrupos.getText().toString());
            contato.setEmail(editTextEmail.getText().toString());

            contato.setTipoDatasEspecias(String.valueOf(spinnerDatasEspecias.getSelectedItemPosition()));
            contato.setTipoEmail(String.valueOf(spinnerEmail.getSelectedItemPosition()));
            contato.setTipoEndereco(String.valueOf(spinnerEndereco.getSelectedItemPosition()));
            contato.setTipoTelefone(String.valueOf(spinnerTelefone.getSelectedItemPosition()));

            repositorioContato.inserirContato(contato);

        }catch (Exception ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao inserir os dados: " + ex.getMessage());
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }
    }

    private void exibeData(){

        Calendar calendar = Calendar.getInstance();

        int ano =  calendar.get(Calendar.YEAR);
        int mes =  calendar.get(Calendar.MONTH);
        int dia =  calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dlg = new DatePickerDialog(this, new SelecionaDataListener(), ano, mes, dia);
        dlg.show();
    }

    private class ExibeDataListener implements View.OnClickListener, View.OnFocusChangeListener{

        @Override
        public void onClick(View v) {
            exibeData();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){
                exibeData();
            }
        }
    }

    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String dt = DateUtils.dateToString(year, monthOfYear, dayOfMonth);
            Date data = DateUtils.getDate(year, monthOfYear, dayOfMonth);

            editTextDatasEspecias.setText(dt);

            contato.setDatasEspeciais(data);
        }
    }
}


