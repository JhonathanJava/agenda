package br.com.estudoandroid.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import br.com.estudoandroid.agenda.ListaAlunosActivity;
import br.com.estudoandroid.agenda.R;
import br.com.estudoandroid.agenda.modelo.Aluno;

/**
 * Created by Jhonathan on 06/04/2017.
 */

public class AlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;
        if(view == null ){
            view = inflater.inflate(R.layout.list_item,parent,false);
        }

        TextView campoNome = (TextView) view.findViewById(R.id.item_nome);
        campoNome.setText(aluno.getNome());

        TextView campoEndereco = (TextView) view.findViewById(R.id.item_endereco);
        if(campoEndereco != null) {
            campoEndereco.setText(aluno.getEndereco());
        }
        TextView campoSite = (TextView) view.findViewById(R.id.item_site);
        if(campoSite != null) {
            campoSite.setText(aluno.getSite());
        }
        TextView campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        campoTelefone.setText(aluno.getTelefone());

        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        String caminhoFoto = aluno.getCaminhoFoto();
        if(caminhoFoto != null) {
            try {
                Bitmap bitmapRotacionado = carrega(caminhoFoto);
                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmapRotacionado, 100, 100, true);
                campoFoto.setImageBitmap(bitmapReduzido);
                campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    public  Bitmap carrega(String caminhoFoto) throws IOException {
        ExifInterface exif = new ExifInterface(caminhoFoto);
        String orientacao = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int codigoOrientacao = Integer.parseInt(orientacao);

        switch (codigoOrientacao) {
            case ExifInterface.ORIENTATION_NORMAL:
                return abreFotoERotaciona(caminhoFoto, 0);
            case ExifInterface.ORIENTATION_ROTATE_90:
                return abreFotoERotaciona(caminhoFoto, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return abreFotoERotaciona(caminhoFoto, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return abreFotoERotaciona(caminhoFoto, 270);
        }
        return null;
    }

        private Bitmap abreFotoERotaciona(String caminhoFoto, int angulo) {
            // Abre o bitmap a partir do caminho da foto
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);

            // Prepara a operação de rotação com o ângulo escolhido
            Matrix matrix = new Matrix();
            matrix.postRotate(angulo);

            // Cria um novo bitmap a partir do original já com a rotação aplicada
            return Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(),
                    matrix, true);
        }
}
