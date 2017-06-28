# Pujangga - Indonesian Natural Language Processing REST API

This is an interface for InaNLP and Deeplearning4j's Word2Vec for Indonesian (Bahasa Indonesia)

![screenshot](https://panggi.s3.amazonaws.com/public/media/pujangga.jpg "Screenshot of Pujangga request and response using Paw REST Client")

### Credits:

* [Dr. Eng. Ayu Purwarianti, ST.,MT., et al](http://ieeexplore.ieee.org/document/7803103/)
* [Yudi Wibisono, MT.](https://yudiwbs.wordpress.com/2016/11/17/word2vec-untuk-bahasa-indonesia/)

## Local Setup

1. Install scala 2.12.2 and Lightbend Activator

2. Clone the project

  ```
  $ git clone git@github.com:panggi/pujangga.git
  ```

3. Download the dependencies

  ```
  $ cd pujangga
  $ activator
  ```
  
4. Pretrained word2vec model can be downloaded here [https://drive.google.com/uc?id=0B5YTktu2dOKKNUY1OWJORlZTcUU&export=download](https://drive.google.com/uc?id=0B5YTktu2dOKKNUY1OWJORlZTcUU&export=download)

5. Run Application

  ```
  $ export WORD2VEC_FILE=/path/to/word2vec_wiki_id   
  $ activator run 
  ```
 
6. Access on `http://localhost:9000`

## Endpoints

### Stemmer

Request:

`POST /stemmer`

```
{
  "string": "Prof. Habibie akan melakukan kunjungan resmi ke PT. Pindad di Bandung"
}
```

Response:

```
{
  "status": "success",
  "data": "prof Habibie akan laku kunjung resmi ke pt Pindad di bandung"
}
```

### Phrase Chunker

Request:

`POST /phrasechunker`

```
{
  "string": "Prof. Habibie akan melakukan kunjungan resmi ke PT. Pindad di Bandung"
}
```

Response:

```
{
  "status": "success",
  "data": {
    "map": {
      "Pindad ": "NP",
      "Prof. Habibie ": "NP",
      ".": ".",
      "di Bandung ": "PP",
      "akan melakukan kunjungan resmi ke PT ": "VP"
    },
    "list": [
      "NP",
      "VP",
      "NP",
      "PP"
    ]
  }
}
```

### Part-of-Speech Tagger

Request:

`POST /postagger`

```
{
  "string": "Prof. Habibie akan melakukan kunjungan resmi ke PT. Pindad di Bandung"
}
```

Response:

```
{
  "status": "success",
  "data": {
    "map": {
      "resmi": "JJ",
      ".": ".",
      "akan": "MD",
      "ke": "IN",
      "di": "IN",
      "Bandung": "NNP",
      "Pindad": "NNP",
      "PT": "NN",
      "Prof.": "NNP",
      "kunjungan": "NN",
      "Habibie": "NNP",
      "melakukan": "VBT"
    },
    "list": [
      "NNP",
      "NNP",
      "MD",
      "VBT",
      "NN",
      "JJ",
      "IN",
      "NN",
      "NNP",
      "IN",
      "NNP"
    ]
  }
}
```

### Named-Entity Tagger

Request:

`POST /netagger`

```
{
  "string": "Prof. Habibie akan melakukan kunjungan resmi ke PT. Pindad di Bandung"
}
```

Response:

```
{
  "status": "success",
  "data": [
    "OTHER",
    "PERSON-B",
    "OTHER",
    "OTHER",
    "OTHER",
    "OTHER",
    "OTHER",
    "LOCATION-B",
    "OTHER",
    "PERSON-B",
    "OTHER",
    "LOCATION-B"
  ]
}
```

### Formalizer

Request:

`POST /formalizer`

```
{
  "string": "Sis, lu bisa nggak pesenin gw sepatu newbalance tipe 960? gpl ya. hati2 sama penipuan anak 4l4y"
}
```

Response:

```
{
  "status": "success",
  "data": "Sis , kamu bisa tidak pesankan saya sepatu newbalance tipe 960 ? tidak pakai lama iya . hati-hati sama penipuan anak norak "
}
```

### Stopwords Removal

Request:

`POST /stopwords`

```
{
  "string": "Prof. Habibie akan melakukan kunjungan resmi ke PT. Pindad di Bandung"
}
```

Response:

```
{
  "status": "success",
  "data": "Prof. Habibie kunjungan resmi PT . Pindad Bandung "
}
```

### Sentence Tokenizer

Request:

`POST /sentence/tokenizer`

```
{
  "string": "Saya pergi ke (bagian kanan) rumah sakit Prof. Dr. Soerojo."
}
```

Response:

```
{
  "status": "success",
  "data": [
    "Saya",
    "pergi",
    "ke",
    "(",
    "bagian",
    "kanan",
    ")",
    "rumah",
    "sakit",
    "Prof.",
    "Dr.",
    "Soerojo",
    "."
  ]
}
```

### Sentence Tokenizer with Composite Words

Request:

`POST /sentence/tokenizer/composite`

```
{
  "string": "Saya pergi ke (bagian kanan) rumah sakit Prof. Dr. Soerojo."
}
```

Response:

```
{
  "status": "success",
  "data": [
    "Saya",
    "pergi",
    "ke",
    "(",
    "bagian kanan",
    ")",
    "rumah sakit",
    "Prof.",
    "Dr.",
    "Soerojo",
    "."
  ]
}
```

### Sentence Splitter

Request:

`POST /sentence/splitter`

```
{
  "string": "Michael Jeffrey Jordan dilahirkan di Brooklyn, New York, Amerika Serikat, pada 17 Februari 1963 adalah pemain bola basket profesional asal Amerika. Michael Jordan merupakan pemain terkenal di dunia dalam cabang olahraga itu. Setidaknya ia enam kali merebut kejuaraan NBA bersama kelompok Chicago Bulls (1991-1993, 1996-1998). Ia memiliki tinggi badan 198 cm dan merebut gelar pemain terbaik."
}
```

Response:

```
{
  "status": "success",
  "data": [
    "Michael Jeffrey Jordan dilahirkan di Brooklyn, New York, Amerika Serikat, pada 17 Februari 1963 adalah pemain bola basket profesional asal Amerika .",
    "Michael Jordan merupakan pemain terkenal di dunia dalam cabang olahraga itu .",
    "Setidaknya ia enam kali merebut kejuaraan NBA bersama kelompok Chicago Bulls (1991-1993, 1996-1998) .",
    "Ia memiliki tinggi badan 198 cm dan merebut gelar pemain terbaik ."
  ]
}
```

### Word2Vec Nearest Words

Request:

`POST /word2vec/nearestwords`

```
{
  "string": "mobil",
  "n": 10
}
```

Response:

```
{
  "status": "success",
  "data": [
    "motor",
    "dikendarai",
    "sepeda",
    "truk",
    "motornya",
    "mengemudikan",
    "mobil-mobil",
    "mobilnya",
    "mengendarai",
    "pengemudi"
  ]
}
```

### Word2Vec Arithmetic

Request:

`POST /word2vec/arithmetic`

```
{
  "first_string": "serang",
  "second_string": "malang",
  "third_string": "surabaya",
  "n": 10
}
```

Response:

```
{
  "status": "success",
  "data": [
    "serang",
    "lebak",
    "puloampel",
    "keserangan",
    "bogor",
    "waringinkurung",
    "jawilan",
    "cianjur",
    "garut",
    "padarincang"
  ]
}
```

### Word2Vec Similarity

Request:

`POST /word2vec/similarity`

```
{
  "first_string": "sore",
  "second_string": "petang"
}
```

Response:

```
{
  "status": "success",
  "data": 0.7748607993125916
}
```


