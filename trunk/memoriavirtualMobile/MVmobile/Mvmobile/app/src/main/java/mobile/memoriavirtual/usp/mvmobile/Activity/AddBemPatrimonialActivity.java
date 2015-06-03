package mobile.memoriavirtual.usp.mvmobile.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormAutor;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormDescricao;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormDisponibilidade;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormEstado;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormInformacoes;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormMidia;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormOutros;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormProcedencia;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormProducao;
import mobile.memoriavirtual.usp.mvmobile.Model.BemPatrimonial;
import mobile.memoriavirtual.usp.mvmobile.R;
import mobile.memoriavirtual.usp.mvmobile.Utils.Mask;
import mobile.memoriavirtual.usp.mvmobile.Utils.Utils;

public class AddBemPatrimonialActivity extends ActionBarActivity implements FormMidia.OnFragmentInteractionListener,FormInformacoes.OnFragmentInteractionListener, FormAutor.OnFragmentInteractionListener,FormProducao.OnFragmentInteractionListener,FormDescricao.OnFragmentInteractionListener,FormEstado.OnFragmentInteractionListener,FormDisponibilidade.OnFragmentInteractionListener,FormProcedencia.OnFragmentInteractionListener,FormOutros.OnFragmentInteractionListener {

    AddBemPatrimonialAdapter mAdapter;
    ViewPager mPager;
    BemPatrimonial mBemPatrimonial;
    String mCurrentPhotoPath;
    Bitmap imageCaptured;

    //Datas
    private DatePickerDialog cadastro_data_pickerDialog;
    private DatePickerDialog cadastro_data_retorno_pickerDialog;
    private SimpleDateFormat dateFormatter;

    Integer mIndex;
    Boolean isToEdit;

    //Midia
    ImageView cadastro_foto;

    //Informações gerais
    CheckBox cadastro_externo;
    EditText cadastro_tipo;
    EditText cadastro_num_registro;
    EditText cadastro_titulo_principal;
    EditText cadastro_complemento;
    EditText cadastro_colecao;
    EditText cadastro_latitude;
    EditText cadastro_longitude;

    //Autor
    EditText cadastro_autoria;

    //Produção
    EditText cadastro_local;
    EditText cadastro_ano;
    EditText cadastro_edicao;
    EditText cadastro_outras_responsabilidades;

    //Descrição
    EditText cadastro_caracteristicas;
    EditText cadastro_dimensoes_quantificacoes;
    EditText cadastro_condicao_topografica;
    EditText cadastro_uso;
    EditText cadastro_num_ambientes;
    EditText cadastro_num_pavimentos;
    CheckBox cadastro_alcova;
    CheckBox cadastro_porao;
    CheckBox cadastro_sotao;
    EditText cadastro_meio_antropico;
    EditText cadastro_carac_ambientais;
    EditText cadastro_sitio_paisagem;
    EditText cadastro_agua_proxima;
    EditText cadastro_vegetacao;
    EditText cadastro_exposicao;
    EditText cadastro_uso_atual;
    EditText cadastro_outros;
    EditText cadastro_area_total;
    EditText cadastro_altura_fachada_frontal;
    EditText cadastro_altura_fachada_posterior;
    EditText cadastro_largura;
    EditText cadastro_altura;
    EditText cadastro_profundidade;
    EditText cadastro_altura_cumeeira;
    EditText cadastro_altura_total;
    EditText cadastro_pe_direito_terreo;
    EditText cadastro_tipo_pe_direito;
    EditText cadastro_comprimento;
    EditText cadastro_localizacao_fisica;
    EditText cadastro_conteudo;
    EditText cadastro_meio_acesso;
    EditText cadastro_notas;

    //Estado
    EditText cadastro_estado_preservacao;
    EditText cadastro_estado_convervacao;
    EditText cadastro_notas_estado_convervacao;

    //Disponibilidade
    EditText cadastro_disponibilidade;
    EditText cadastro_condicao_acesso;
    EditText cadastro_condicao_reproducao;
    EditText cadastro_protecao;
    EditText cadastro_numero_processo;
    EditText cadastro_data_retorno;
    EditText cadastro_notas_uso_aproveitamento;

    //Procedência
    EditText cadastro_tipo_aquisicao;
    EditText cadastro_valor_venal;
    EditText cadastro_data;
    EditText cadastro_primeiro_proprietario;
    EditText cadastro_dados_transacao;
    EditText cadastro_historico;
    EditText cadastro_instrumento_pesquisa;

    //Outros
    EditText cadastro_assuntos;
    EditText cadastro_descritores;
    EditText cadastro_fontes_informacao;
    EditText cadastro_pesquisadores;
    EditText cadastro_relacionar_outros_bens;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bem_patrimonial);

        //Botao de voltar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAdapter = new AddBemPatrimonialAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(9); //nao destroi as fragments escondidos

        //Valores do extra, janela anterior
        final BemPatrimonial bemPatrimonial = (BemPatrimonial) getIntent().getSerializableExtra("bem");
        mIndex = getIntent().getIntExtra("index",1);

        //Se passou um bem patrimonial entao irá editar
        if(bemPatrimonial == null)
            isToEdit = false;
        else
            isToEdit = true;

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setReferenciasDosCampos();

                //Se passou um bem patrimonial entao irá editar, preenche os campos com o bem patrimonial passado
                if(bemPatrimonial != null && mBemPatrimonial == null){
                    mBemPatrimonial = bemPatrimonial;
                    setTitle(R.string.title_activity_edit_bem_patrimonial);
                    preencheCampos(bemPatrimonial);
                }
            }
            @Override

            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_bem_patrimonial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add_bem_patrimonial:
                addTouched();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onFragmentInteraction() {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    private void addTouched()
    {
        new AlertDialog.Builder(this)
                .setTitle("Deseja mesmo salvar?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //setReferenciasDosCampos();
                        getBemPatrimonialDosCampos();

                        try {
                            //Se passou um bem patrimonial entao irá editar, preenche os campos com o bem patrimonial passado
                            if(isToEdit) {
                                Utils.editarBemPatrimonialCache(mBemPatrimonial,mIndex);
                            }else{
                                Utils.salvarBemPatrimonialCache(mBemPatrimonial);
                            }
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    private void setReferenciasDosCampos()
    {
        //Midia
        cadastro_foto = (ImageView) findViewById(R.id.cadastro_foto);

        //Informações gerais
        cadastro_externo = (CheckBox) findViewById(R.id.cadastro_externo);
        cadastro_tipo = (EditText) findViewById(R.id.cadastro_tipo);
        cadastro_num_registro = (EditText) findViewById(R.id.cadastro_num_registro);
        cadastro_titulo_principal = (EditText) findViewById(R.id.cadastro_titulo_principal);
        cadastro_complemento = (EditText) findViewById(R.id.cadastro_complemento);
        cadastro_colecao = (EditText) findViewById(R.id.cadastro_colecao);
        cadastro_latitude = (EditText) findViewById(R.id.cadastro_latitude);
        cadastro_longitude = (EditText) findViewById(R.id.cadastro_longitude);

        //Autor
        cadastro_autoria = (EditText) findViewById(R.id.cadastro_autoria);

        //Produção

        cadastro_local = (EditText) findViewById(R.id.cadastro_local);
        cadastro_ano = (EditText) findViewById(R.id.cadastro_ano);
        cadastro_edicao = (EditText) findViewById(R.id.cadastro_edicao);
        cadastro_outras_responsabilidades = (EditText) findViewById(R.id.cadastro_outras_responsabilidades);

        //Descrição
        cadastro_caracteristicas = (EditText) findViewById(R.id.cadastro_caracteristicas);
        cadastro_dimensoes_quantificacoes = (EditText) findViewById(R.id.cadastro_dimensoes_quantificacoes);
        cadastro_condicao_topografica = (EditText) findViewById(R.id.cadastro_condicao_topografica);
        cadastro_uso = (EditText) findViewById(R.id.cadastro_uso);
        cadastro_num_ambientes = (EditText) findViewById(R.id.cadastro_num_ambientes);
        cadastro_num_pavimentos = (EditText) findViewById(R.id.cadastro_num_pavimentos);
        cadastro_alcova = (CheckBox) findViewById(R.id.cadastro_alcova);
        cadastro_porao = (CheckBox) findViewById(R.id.cadastro_porao);
        cadastro_sotao = (CheckBox) findViewById(R.id.cadastro_sotao);
        cadastro_meio_antropico = (EditText) findViewById(R.id.cadastro_meio_antropico);
        cadastro_carac_ambientais = (EditText) findViewById(R.id.cadastro_carac_ambientais);
        cadastro_sitio_paisagem = (EditText) findViewById(R.id.cadastro_sitio_paisagem);
        cadastro_agua_proxima = (EditText) findViewById(R.id.cadastro_agua_proxima);
        cadastro_vegetacao = (EditText) findViewById(R.id.cadastro_vegetacao);
        cadastro_exposicao = (EditText) findViewById(R.id.cadastro_exposicao);
        cadastro_uso_atual = (EditText) findViewById(R.id.cadastro_uso_atual);
        cadastro_outros = (EditText) findViewById(R.id.cadastro_outros);
        cadastro_area_total = (EditText) findViewById(R.id.cadastro_area_total);
        cadastro_altura_fachada_frontal = (EditText) findViewById(R.id.cadastro_altura_fachada_frontal);
        cadastro_altura_fachada_posterior = (EditText) findViewById(R.id.cadastro_altura_fachada_posterior);
        cadastro_largura = (EditText) findViewById(R.id.cadastro_largura);
        cadastro_altura = (EditText) findViewById(R.id.cadastro_altura);
        cadastro_profundidade = (EditText) findViewById(R.id.cadastro_profundidade);
        cadastro_altura_cumeeira = (EditText) findViewById(R.id.cadastro_altura_cumeeira);
        cadastro_altura_total = (EditText) findViewById(R.id.cadastro_altura_total);
        cadastro_pe_direito_terreo = (EditText) findViewById(R.id.cadastro_pe_direito_terreo);
        cadastro_tipo_pe_direito = (EditText) findViewById(R.id.cadastro_tipo_pe_direito);
        cadastro_comprimento = (EditText) findViewById(R.id.cadastro_comprimento);
        cadastro_localizacao_fisica = (EditText) findViewById(R.id.cadastro_localizacao_fisica);
        cadastro_conteudo = (EditText) findViewById(R.id.cadastro_conteudo);
        cadastro_meio_acesso = (EditText) findViewById(R.id.cadastro_meio_acesso);
        cadastro_notas = (EditText) findViewById(R.id.cadastro_notas);

        //Estado
        cadastro_estado_preservacao = (EditText) findViewById(R.id.cadastro_estado_preservacao);
        cadastro_estado_convervacao = (EditText) findViewById(R.id.cadastro_estado_convervacao);
        cadastro_notas_estado_convervacao = (EditText) findViewById(R.id.cadastro_notas_estado_convervacao);

        //Disponibilidade
        cadastro_disponibilidade = (EditText) findViewById(R.id.cadastro_disponibilidade);
        cadastro_condicao_acesso = (EditText) findViewById(R.id.cadastro_condicao_acesso);
        cadastro_condicao_reproducao = (EditText) findViewById(R.id.cadastro_condicao_reproducao);
        cadastro_protecao = (EditText) findViewById(R.id.cadastro_protecao);
        cadastro_numero_processo = (EditText) findViewById(R.id.cadastro_numero_processo);
        cadastro_data_retorno = (EditText) findViewById(R.id.cadastro_data_retorno);
        cadastro_notas_uso_aproveitamento = (EditText) findViewById(R.id.cadastro_notas_uso_aproveitamento);

        //Procedência
        cadastro_tipo_aquisicao = (EditText) findViewById(R.id.cadastro_tipo_aquisicao);
        cadastro_valor_venal = (EditText) findViewById(R.id.cadastro_valor_venal);
        cadastro_data = (EditText) findViewById(R.id.cadastro_data);

        cadastro_primeiro_proprietario = (EditText) findViewById(R.id.cadastro_primeiro_proprietario);
        cadastro_dados_transacao = (EditText) findViewById(R.id.cadastro_dados_transacao);
        cadastro_historico = (EditText) findViewById(R.id.cadastro_historico);
        cadastro_instrumento_pesquisa = (EditText) findViewById(R.id.cadastro_instrumento_pesquisa);

        //Outros
        cadastro_assuntos = (EditText) findViewById(R.id.cadastro_assuntos);
        cadastro_descritores = (EditText) findViewById(R.id.cadastro_descritores);
        cadastro_fontes_informacao = (EditText) findViewById(R.id.cadastro_fontes_informacao);
        cadastro_pesquisadores = (EditText) findViewById(R.id.cadastro_pesquisadores);
        cadastro_relacionar_outros_bens = (EditText) findViewById(R.id.cadastro_relacionar_outros_bens);

        setDatesEditText();
    }

    private void setDatesEditText() {
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        cadastro_data.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    cadastro_data_pickerDialog.show();
            }
        });

        cadastro_data_retorno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    cadastro_data_retorno_pickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        cadastro_data_pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                cadastro_data.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        cadastro_data_retorno_pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                cadastro_data_retorno.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void getBemPatrimonialDosCampos()
    {
        //Midia
        //Se tirou uma imagem entao coloca no bem patrimonial
        if(imageCaptured != null)
            mBemPatrimonial.setCadastro_image(Utils.bitMapToString(imageCaptured));

        //Informações gerais
        mBemPatrimonial.setCadastro_externo(cadastro_externo.isChecked() ? "1" : "0");
        mBemPatrimonial.setCadastro_tipo(cadastro_tipo.getText().toString());
        mBemPatrimonial.setCadastro_num_registro(cadastro_num_registro.getText().toString());
        mBemPatrimonial.setCadastro_titulo_principal(cadastro_titulo_principal.getText().toString());
        mBemPatrimonial.setCadastro_complemento(cadastro_complemento.getText().toString());
        mBemPatrimonial.setCadastro_colecao(cadastro_colecao.getText().toString());
        mBemPatrimonial.setCadastro_latitude(cadastro_latitude.getText().toString());
        mBemPatrimonial.setCadastro_longitude(cadastro_longitude.getText().toString());

        //Autor
        mBemPatrimonial.setCadastro_autoria(cadastro_autoria.getText().toString());

        //Producao
        mBemPatrimonial.setCadastro_local(cadastro_local.getText().toString());
        mBemPatrimonial.setCadastro_ano(cadastro_ano.getText().toString());
        mBemPatrimonial.setCadastro_edicao(cadastro_edicao.getText().toString());
        mBemPatrimonial.setCadastro_outras_responsabilidades(cadastro_outras_responsabilidades.getText().toString());

        //Descricao
        mBemPatrimonial.setCadastro_caracteristicas(cadastro_caracteristicas.getText().toString());
        mBemPatrimonial.setCadastro_dimensoes_quantificacoes(cadastro_dimensoes_quantificacoes.getText().toString());
        mBemPatrimonial.setCadastro_condicao_topografica(cadastro_condicao_topografica.getText().toString());
        mBemPatrimonial.setCadastro_uso(cadastro_uso.getText().toString());
        mBemPatrimonial.setCadastro_num_ambientes(cadastro_num_ambientes.getText().toString());
        mBemPatrimonial.setCadastro_num_pavimentos(cadastro_num_pavimentos.getText().toString());
        mBemPatrimonial.setCadastro_alcova(cadastro_alcova.isChecked() ? "1" : "0");
        mBemPatrimonial.setCadastro_porao(cadastro_porao.isChecked() ? "1" : "0");
        mBemPatrimonial.setCadastro_sotao(cadastro_sotao.isChecked() ? "1" : "0");
        mBemPatrimonial.setCadastro_meio_antropico(cadastro_meio_antropico.getText().toString());
        mBemPatrimonial.setCadastro_carac_ambientais(cadastro_carac_ambientais.getText().toString());
        mBemPatrimonial.setCadastro_sitio_paisagem(cadastro_sitio_paisagem.getText().toString());
        mBemPatrimonial.setCadastro_agua_proxima(cadastro_agua_proxima.getText().toString());
        mBemPatrimonial.setCadastro_vegetacao(cadastro_vegetacao.getText().toString());
        mBemPatrimonial.setCadastro_exposicao(cadastro_exposicao.getText().toString());
        mBemPatrimonial.setCadastro_uso_atual(cadastro_uso_atual.getText().toString());
        mBemPatrimonial.setCadastro_outros(cadastro_outros.getText().toString());
        mBemPatrimonial.setCadastro_area_total(cadastro_area_total.getText().toString());
        mBemPatrimonial.setCadastro_altura_fachada_frontal(cadastro_altura_fachada_frontal.getText().toString());
        mBemPatrimonial.setCadastro_altura_fachada_posterior(cadastro_altura_fachada_posterior.getText().toString());
        mBemPatrimonial.setCadastro_largura(cadastro_largura.getText().toString());
        mBemPatrimonial.setCadastro_altura(cadastro_altura.getText().toString());
        mBemPatrimonial.setCadastro_profundidade(cadastro_profundidade.getText().toString());
        mBemPatrimonial.setCadastro_altura_cumeeira(cadastro_altura_cumeeira.getText().toString());
        mBemPatrimonial.setCadastro_altura_total(cadastro_altura_total.getText().toString());
        mBemPatrimonial.setCadastro_pe_direito_terreo(cadastro_pe_direito_terreo.getText().toString());
        mBemPatrimonial.setCadastro_tipo_pe_direito(cadastro_tipo_pe_direito.getText().toString());
        mBemPatrimonial.setCadastro_comprimento(cadastro_comprimento.getText().toString());
        mBemPatrimonial.setCadastro_localizacao_fisica(cadastro_localizacao_fisica.getText().toString());
        mBemPatrimonial.setCadastro_conteudo(cadastro_conteudo.getText().toString());
        mBemPatrimonial.setCadastro_meio_acesso(cadastro_meio_acesso.getText().toString());
        mBemPatrimonial.setCadastro_notas(cadastro_notas.getText().toString());

        //Estado
        mBemPatrimonial.setCadastro_estado_preservacao(cadastro_estado_preservacao.getText().toString());
        mBemPatrimonial.setCadastro_estado_convervacao(cadastro_estado_convervacao.getText().toString());
        mBemPatrimonial.setCadastro_notas_estado_convervacao(cadastro_notas_estado_convervacao.getText().toString());

        //Disponibilidade
        mBemPatrimonial.setCadastro_disponibilidade(cadastro_disponibilidade.getText().toString());
        mBemPatrimonial.setCadastro_condicao_acesso(cadastro_condicao_acesso.getText().toString());
        mBemPatrimonial.setCadastro_condicao_reproducao(cadastro_condicao_reproducao.getText().toString());
        mBemPatrimonial.setCadastro_protecao(cadastro_protecao.getText().toString());
        mBemPatrimonial.setCadastro_numero_processo(cadastro_numero_processo.getText().toString());
        mBemPatrimonial.setCadastro_data_retorno(cadastro_data_retorno.getText().toString());
        mBemPatrimonial.setCadastro_notas_uso_aproveitamento(cadastro_notas_uso_aproveitamento.getText().toString());

        //Procedencia
        mBemPatrimonial.setCadastro_tipo_aquisicao(cadastro_tipo_aquisicao.getText().toString());
        mBemPatrimonial.setCadastro_valor_venal(cadastro_valor_venal.getText().toString());
        mBemPatrimonial.setCadastro_data(cadastro_data.getText().toString());
        mBemPatrimonial.setCadastro_primeiro_proprietario(cadastro_primeiro_proprietario.getText().toString());
        mBemPatrimonial.setCadastro_dados_transacao(cadastro_dados_transacao.getText().toString());
        mBemPatrimonial.setCadastro_historico(cadastro_historico.getText().toString());
        mBemPatrimonial.setCadastro_instrumento_pesquisa(cadastro_instrumento_pesquisa.getText().toString());

        //Assuntos
        mBemPatrimonial.setCadastro_assuntos(cadastro_assuntos.getText().toString());
        mBemPatrimonial.setCadastro_descritores(cadastro_descritores.getText().toString());
        mBemPatrimonial.setCadastro_fontes_informacao(cadastro_fontes_informacao.getText().toString());
        mBemPatrimonial.setCadastro_pesquisadores(cadastro_pesquisadores.getText().toString());
        mBemPatrimonial.setCadastro_relacionar_outros_bens(cadastro_relacionar_outros_bens.getText().toString());
    }

    public void preencheCampos(BemPatrimonial bemPatrimonial){

        //Midia
        cadastro_foto.setImageBitmap(Utils.stringToBitMap(bemPatrimonial.getCadastro_image()));

        //Informações gerais
        cadastro_externo.setChecked((bemPatrimonial.getCadastro_externo().equals("1")) ? true : false);
        cadastro_tipo.setText(bemPatrimonial.getCadastro_tipo());
        cadastro_num_registro.setText(bemPatrimonial.getCadastro_num_registro());
        cadastro_titulo_principal.setText(bemPatrimonial.getCadastro_titulo_principal());
        cadastro_complemento.setText(bemPatrimonial.getCadastro_complemento());
        cadastro_colecao.setText(bemPatrimonial.getCadastro_colecao());
        cadastro_latitude.setText(bemPatrimonial.getCadastro_latitude());
        cadastro_longitude.setText(bemPatrimonial.getCadastro_longitude());

        //Autor
        cadastro_autoria.setText(bemPatrimonial.getCadastro_autoria());

        //Producao
        cadastro_local.setText(bemPatrimonial.getCadastro_local());
        cadastro_ano.setText(bemPatrimonial.getCadastro_ano());
        cadastro_edicao.setText(bemPatrimonial.getCadastro_edicao());
        cadastro_outras_responsabilidades.setText(bemPatrimonial.getCadastro_outras_responsabilidades());

        //Descricao
        cadastro_caracteristicas.setText(bemPatrimonial.getCadastro_caracteristicas());
        cadastro_dimensoes_quantificacoes.setText(bemPatrimonial.getCadastro_dimensoes_quantificacoes());
        cadastro_condicao_topografica.setText(bemPatrimonial.getCadastro_condicao_topografica());
        cadastro_uso.setText(bemPatrimonial.getCadastro_uso());
        cadastro_num_ambientes.setText(bemPatrimonial.getCadastro_num_ambientes());
        cadastro_num_pavimentos.setText(bemPatrimonial.getCadastro_num_pavimentos());

        cadastro_alcova.setChecked((bemPatrimonial.getCadastro_alcova().equals("1")) ? true : false);

        cadastro_porao.setChecked((bemPatrimonial.getCadastro_porao().equals("1")) ? true : false);
        cadastro_sotao.setChecked((bemPatrimonial.getCadastro_sotao().equals("1")) ? true : false);

        cadastro_meio_antropico.setText(bemPatrimonial.getCadastro_meio_antropico());
        cadastro_carac_ambientais.setText(bemPatrimonial.getCadastro_carac_ambientais());
        cadastro_sitio_paisagem.setText(bemPatrimonial.getCadastro_sitio_paisagem());
        cadastro_agua_proxima.setText(bemPatrimonial.getCadastro_agua_proxima());
        cadastro_vegetacao.setText(bemPatrimonial.getCadastro_vegetacao());
        cadastro_exposicao.setText(bemPatrimonial.getCadastro_exposicao());
        cadastro_uso_atual.setText(bemPatrimonial.getCadastro_uso_atual());
        cadastro_outros.setText(bemPatrimonial.getCadastro_outros());
        cadastro_area_total.setText(bemPatrimonial.getCadastro_area_total());
        cadastro_altura_fachada_frontal.setText(bemPatrimonial.getCadastro_altura_fachada_frontal());
        cadastro_altura_fachada_posterior.setText(bemPatrimonial.getCadastro_altura_fachada_posterior());
        cadastro_largura.setText(bemPatrimonial.getCadastro_largura());
        cadastro_altura.setText(bemPatrimonial.getCadastro_altura());
        cadastro_profundidade.setText(bemPatrimonial.getCadastro_profundidade());

        cadastro_altura_cumeeira.setText(bemPatrimonial.getCadastro_altura_cumeeira());
        cadastro_altura_total.setText(bemPatrimonial.getCadastro_altura_total());
        cadastro_pe_direito_terreo.setText(bemPatrimonial.getCadastro_pe_direito_terreo());
        cadastro_tipo_pe_direito.setText(bemPatrimonial.getCadastro_tipo_pe_direito());
        cadastro_comprimento.setText(bemPatrimonial.getCadastro_comprimento());
        cadastro_localizacao_fisica.setText(bemPatrimonial.getCadastro_localizacao_fisica());
        cadastro_conteudo.setText(bemPatrimonial.getCadastro_conteudo());
        cadastro_meio_acesso.setText(bemPatrimonial.getCadastro_meio_acesso());
        cadastro_notas.setText(bemPatrimonial.getCadastro_notas());

        //Estado
        cadastro_estado_preservacao.setText(bemPatrimonial.getCadastro_estado_preservacao());
        cadastro_estado_convervacao.setText(bemPatrimonial.getCadastro_estado_convervacao());
        cadastro_notas_estado_convervacao.setText(bemPatrimonial.getCadastro_notas_estado_convervacao());

        //Disponibilidadget
        cadastro_disponibilidade.setText(bemPatrimonial.getCadastro_disponibilidade());
        cadastro_condicao_acesso.setText(bemPatrimonial.getCadastro_condicao_acesso());
        cadastro_condicao_reproducao.setText(bemPatrimonial.getCadastro_condicao_reproducao());
        cadastro_protecao.setText(bemPatrimonial.getCadastro_protecao());
        cadastro_numero_processo.setText(bemPatrimonial.getCadastro_numero_processo());
        cadastro_data_retorno.setText(bemPatrimonial.getCadastro_data_retorno());
        cadastro_notas_uso_aproveitamento.setText(bemPatrimonial.getCadastro_notas_uso_aproveitamento());

        //Procedencia
        cadastro_tipo_aquisicao.setText(bemPatrimonial.getCadastro_tipo_aquisicao());
        cadastro_valor_venal.setText(bemPatrimonial.getCadastro_valor_venal());
        cadastro_data.setText(bemPatrimonial.getCadastro_data());
        cadastro_primeiro_proprietario.setText(bemPatrimonial.getCadastro_primeiro_proprietario());
        cadastro_dados_transacao.setText(bemPatrimonial.getCadastro_dados_transacao());
        cadastro_historico.setText(bemPatrimonial.getCadastro_historico());
        cadastro_instrumento_pesquisa.setText(bemPatrimonial.getCadastro_instrumento_pesquisa());

        //Assuntos
        cadastro_assuntos.setText(bemPatrimonial.getCadastro_assuntos());
        cadastro_descritores.setText(bemPatrimonial.getCadastro_descritores());
        cadastro_fontes_informacao.setText(bemPatrimonial.getCadastro_fontes_informacao());
        cadastro_pesquisadores.setText(bemPatrimonial.getCadastro_pesquisadores());
        cadastro_relacionar_outros_bens.setText(bemPatrimonial.getCadastro_relacionar_outros_bens());
    }

    private void nextTouched()
    {
        mPager.setCurrentItem(mPager.getCurrentItem()+1);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    //Acoes de botoes
    public void onAddButtonClick(View v) {
        addTouched();
    }
    public void onNextButtonClick(View v) {
        nextTouched();
    }

    public void onTakePictureButtonClick(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("Error to take picture", ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
               // takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                 ///       Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {

                imageCaptured = (Bitmap) data.getExtras().get("data");

                //Bitmap myBitmap = BitmapFactory.decodeFile(dataUri);

                galleryAddPic();
                cadastro_foto.setImageBitmap(imageCaptured);

            } else {
                // usuário não tirou foto.
            }
        }
    }
}