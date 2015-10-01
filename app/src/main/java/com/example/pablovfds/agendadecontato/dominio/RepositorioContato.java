package com.example.pablovfds.agendadecontato.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.example.pablovfds.agendadecontato.dominio.entidades.Contato;

import java.util.Date;

/**
 * Created by pablovfds on 25/09/15.
 */
public class RepositorioContato {

    private SQLiteDatabase sqLiteDatabase;

    public RepositorioContato(SQLiteDatabase sqLiteDatabase){
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void inserirContato(Contato contato){
        ContentValues contentValues = new ContentValues();

        contentValues.put("TELEFONE", contato.getTelefone());
        contentValues.put("TIPOTELEFONE", contato.getTipoTelefone());
        contentValues.put("NOME", contato.getNome());
        contentValues.put("EMAIL", contato.getEmail());
        contentValues.put("TIPOEMAIL", contato.getTipoEmail());
        contentValues.put("ENDERECO", contato.getEndereco());
        contentValues.put("TIPOENDERECO", contato.getTipoEndereco());
        contentValues.put("DATASESPECIAIS", contato.getDatasEspeciais().getTime());
        contentValues.put("TIPODATASESPECIAIS", contato.getTipoDatasEspecias());
        contentValues.put("GRUPOS", contato.getGrupos());

        sqLiteDatabase.insertOrThrow("CONTATO", null, contentValues);

    }

    public ArrayAdapter<Contato> buscaContatos(Context context){

        ArrayAdapter<Contato> arrayAdapterContatos = new ArrayAdapter<Contato>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = sqLiteDatabase.query("CONTATO", null, null, null, null, null, null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {

                Contato contato = new Contato();

                contato.setNome(cursor.getString(1));
                contato.setTelefone(cursor.getString(2));
                contato.setTipoTelefone(cursor.getString(3));
                contato.setEmail(cursor.getString(4));
                contato.setTipoEmail(cursor.getString(5));
                contato.setEndereco(cursor.getString(6));
                contato.setTipoEndereco(cursor.getString(7));
                contato.setDatasEspeciais(new Date(cursor.getLong(8)));
                contato.setTipoDatasEspecias(cursor.getString(9));
                contato.setGrupos(cursor.getString(10));

                arrayAdapterContatos.add(contato);
            }while (cursor.moveToNext());

        }

        return arrayAdapterContatos;
    }

}
