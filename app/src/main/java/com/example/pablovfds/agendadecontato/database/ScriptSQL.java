package com.example.pablovfds.agendadecontato.database;

/**
 * Created by pablovfds on 25/09/15.
 */
public class ScriptSQL {


    public static String getCreateContato(){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS CONTATO ( ");
        stringBuilder.append("_id                INTEGER       NOT NULL ");
        stringBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        stringBuilder.append("NOME           VARCHAR (200), ");
        stringBuilder.append("TELEFONE           VARCHAR (14), ");
        stringBuilder.append("TIPOTELEFONE       VARCHAR (1), ");
        stringBuilder.append("EMAIL              VARCHAR (255), ");
        stringBuilder.append("TIPOEMAIL          VARCHAR (1), ");
        stringBuilder.append("ENDERECO           VARCHAR (255), ");
        stringBuilder.append("TIPOENDERECO       VARCHAR (1), ");
        stringBuilder.append("DATASESPECIAIS     DATE, ");
        stringBuilder.append("TIPODATASESPECIAIS VARCHAR (1), ");
        stringBuilder.append("GRUPOS             VARCHAR (255) ");
        stringBuilder.append(");");

        return stringBuilder.toString();
    }

}
