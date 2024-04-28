#!/usr/bin/env -S scala-cli shebang
//> using scala 3.4
// using platform scala-native

import scala.io.StdIn.readLine
import scala.collection.immutable.LazyList.continually

def makeBool(value: String): String = value match {
    case "1" => "yes"
    case "2" => "no"
    case _ => ""
}

def makeDate(value: String): String = value match {
    case "9999-99-99" => ""
    case _ => value
}

def makeSex(value: String): String = value match {
    case "1" => "female"
    case "2" => "male"
}

def makeDied(value: String): String = value match {
    case "9999-99-99" => "no"
    case _ => "yes"
}

def makeRequiredTreatment(dateDied: String, icu: String, intubed: String, patientType: String   ): String = {
    if (dateDied != "9999-99-99") "yes"
    else if (icu == "1") "yes"
    else if (intubed == "1") "yes"
    else if (patientType == "1") "no"
    else "maybe"
}

def makePatientType(patientType: String): String = patientType match {
    case "1" => "home"
    case "2" => "hospital"
}

def process(line: String): String = {
    val Array(usmer, medicalUnit, sex, patientType, dateDied, intubed, pneumonia, age, pregnant, diabetes, copd, asthma, inmsupr, hipertension, other_disease, cardiovascular, obesity, renal_chronic, tobacco, clasiffication_final, icu) = line.split(",")

    Array(usmer, 
          medicalUnit, 
          makeSex(sex), 
          makePatientType(patientType), 
          makeDate(dateDied), 
          makeBool(intubed), 
          makeBool(pneumonia), 
          age, 
          if (makeSex(sex) == "male") "na" else makeBool(pregnant), 
          makeBool(diabetes), 
          makeBool(copd), 
          makeBool(asthma), 
          makeBool(inmsupr), 
          makeBool(hipertension), 
          makeBool(other_disease), 
          makeBool(cardiovascular), 
          makeBool(obesity), 
          makeBool(renal_chronic), 
          makeBool(tobacco), 
          clasiffication_final, 
          makeBool(icu),
          makeDied(dateDied),
          makeRequiredTreatment(dateDied, icu, intubed, patientType)).mkString(",")
}

def lines = continually(readLine).takeWhile(_ != null)

lines.drop(1)
     .map(process)
     .prepended("USMER,MEDICAL_UNIT,SEX,PATIENT_TYPE,DATE_DIED,INTUBED,PNEUMONIA,AGE,PREGNANT,DIABETES,COPD,ASTHMA,INMSUPR,HIPERTENSION,OTHER_DISEASE,CARDIOVASCULAR,OBESITY,RENAL_CHRONIC,TOBACCO,CLASIFFICATION_FINAL,ICU,DIED,REQUIRED_TREATMENT")
     .foreach(println)