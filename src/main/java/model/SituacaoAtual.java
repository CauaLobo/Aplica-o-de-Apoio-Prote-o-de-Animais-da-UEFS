package model;

/**
 * Enumeração que representa as possíveis situações atuais de um animal no sistema da UEFS.
 * As situações são: Em Observação, Disponível para Adoção e Em Tratamento.
 */
public enum SituacaoAtual {

    /**
     * O animal está em observação, aguardando avaliação ou triagem inicial.
     */
    EM_OBSERVACAO,

    /**
     * O animal está apto e disponível para ser adotado.
     */
    DISPONIVEL_PARA_ADOCAO,

    /**
     * O animal está sob cuidados veterinários e em tratamento.
     */
    EM_TRATAMENTO
}