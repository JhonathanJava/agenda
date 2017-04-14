package br.com.estudoandroid.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.estudoandroid.agenda.R;
import br.com.estudoandroid.agenda.dao.AlunoDAO;

import static android.telephony.SmsMessage.createFromPdu;

/**
 * Created by Jhonathan on 10/04/2017.
 */

public class SMSReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");

        byte[] pdu = (byte[]) pdus[0];
        String formato = (String)intent.getSerializableExtra("format");

        SmsMessage sms = createFromPdu(pdu, formato);
        String telefone = sms.getDisplayOriginatingAddress();
        AlunoDAO dao = new AlunoDAO(context);
        if(dao.ehAluno(telefone)){
            Toast.makeText(context, "Chegou um SMS de Aluno!", Toast.LENGTH_LONG).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }
        dao.close();
    }
}
