package br.com.estudoandroid.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.io.IOException;

import br.com.estudoandroid.agenda.modelo.Aluno;

/**
 * Created by Jhonathan on 31/03/2017.
 */

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoTelefone;
    private final EditText campoEndereco;
    private final EditText campoSite;
    private final ImageView campoFoto;
    private final RatingBar campoNota;

    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity){
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        campoSite = (EditText) activity.findViewById(R.id.formulario_site);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
        aluno = new Aluno();
    }

    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String) campoFoto.getTag());
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    public void carregaImagem(String caminhoFoto) {
        if(caminhoFoto != null) {
            try {
                Bitmap bitmapRotacionado = carrega(caminhoFoto);
                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmapRotacionado, 300, 300, true);
                campoFoto.setImageBitmap(bitmapReduzido);
                campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
                campoFoto.setTag(caminhoFoto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
