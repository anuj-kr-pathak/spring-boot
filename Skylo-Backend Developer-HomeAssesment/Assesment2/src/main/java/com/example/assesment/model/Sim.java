package com.example.assesment.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sim")
public class Sim {

    @Id
    @Column(name = "imsi")
    private String iMSI;
    @Column(name = "pin_1")
    private String pIN1;
    @Column(name = "puk_1")
    private String pUK1;
    @Column(name = "pin_2")
    private String pIN2;
    @Column(name = "puk_2")
    private String pUK2;
    @Column(name = "aam_1")
    private String aAM1;
    @Column(name = "ki_umt_enc")
    private String kiUMTSEnc;
    @Column(name = "acc")
    private String acc;

}
