# Proof of Reserves Licensed to Bitget Limited
## Background

Bitget launches Proof of Reserve (PoR) to improve the security and transparency of user assets. These tools will allow you to independently audit Bitget’s Proof of Reserves as well as verify that[...]

## Introduction
### Build from source
Download the latest version for your operating system and architecture. Also, you can build the source code yourself.

[Download] (https://www.oracle.com/java/technologies/downloads/)Install JDK(Java Development Kit)  
[Download] (https://maven.apache.org/download.cgi.)Install Maven build tool

The minimum prerequisite to build this project requires Java version >= 11, Maven version >= 3.8.4

### Package and compile source code
#### Enter the path for the project
`cd ~/Downloads/proof-of-reserves`

#### Install dependencies
`mvn clean install`

#### Start up
`java -jar proof-of-reserves.jar`

# Technical Description
## What is the Merkle Tree?
Merkle Tree is a data structure, also known as a Hash Tree. Merkle tree stores data in the leaf nodes of the tree structure, and by hashing the data step by step up to the top root node, any chang[...]

### 1. The roles of Merkle tree
- Zero-knowledge proof
- Ensure data immutability
- Ensures data privacy
### 2. Bitget Limited Merkle Tree Definition
#### 2.1 Node Information
Information stored in every tree node includes:
1. hash value;
2. the number of coins contained in the user's asset snapshot (BTC, ETH, USDT for example);
```
   hash value,{"BTC":"BTC amount","ETH":"ETH amount","USDT":"USDT amount"}
   2070b6a5b12f4ea7,{"BTC":1.763,"ETH":362,"USDT":1077200.2274}
```
#### 2.2 Hash Rules
##### Leaf nodes (except padding nodes)
`hash=sha256Function(encryptUid,nonce,balances).substring(0,16)`
- encryptUid: encrypted UID of the user
- nonce: a unique value assigned to each user
- balances: json string composed of the number of coins in the user's asset snapshot, (note: remove the invalid 0 at the end and keep precision of 8 bits)
    - For example：
  ```json
  {"BTC":1.763,"ETH":362,"USDT":1077200.2274}
   ```  
  ##### Parent node
  ```
  Parent node's hash = sha256Function(hash1+hash2,{"BTC":(hash1(BTC amount)+hash2(BTC amount)),"ETH":(hash1(ETH amount)+hash2(ETH amount)),"USDT":(hash1(USDT amount)+hash2(USDT amount))},parent no[...]
   ```
- h1: hash of the left child node of the current node,
- h2: hash of the right child node of the current node,
- level: where the parent node lies in

**Definition of tree node level**：A complete Merkle Tree (full binary tree) requires 2^n leaf node data, leaf node level = n + 1, parent node level = child node level - 1, root node level = 1, l[...]

##### Padding node rules
A complete Merkle Tree (full binary tree) requires 2^n leaf node data, but the actual number of data may not satisfy and may be odd. In such a case, if a node k has no sibling node, then auto padd[...]


###### For example：
| Hash   | balances  |
|--------| -------------|
| hash1  | {"BTC":1,"ETH": 6,"USDT":10}|
| hash2  | {"BTC":2,"ETH":4,"USDT":8}|
| hash3  | {"BTC":5,"ETH":9,"USDT":74}|

Then the padding node hash4 = hash3, stored balances are `{"BTC": 0, "ETH": 0,"USDT": 0}`，as shown in the highlighted node in Figure one：
Figure one
<img src="images/flowChart.jpg" alt="" style="text-align:right;width:500px;"/>

```
Parent node's hash = sha256Function(hash1+hash2,{"BTC":(hash1(BTC amount)+hash2(BTC amount)),"ETH":(hash1(ETH amount)+hash2(ETH amount)),"USDT":(hash1(USDT amount)+hash2(USDT amount))},parent node[...]
```
Thus：
`hash6 = SHA256(hash3 + hash3, {BTC: (2+0), ETH:(1+0), USDT:(12+0)}, level)`

### Verification Principle
#### 1、Verification principle:
According to the definition of Bitget Limited Merkle tree, the hash value of the parent node is calculated from the user's own leaf node up to the root node, and the hash value of the root node is[...]

#### 2、Example：
Combining figure one and the following json text, and based on the user's own leaf node h3 and the information provided by the adjacent node h4, we can calculate out the hash of the parent node h6[...]

#### Verification Steps
1. Take the executable verifier that you need to download on the Bitget platform for your operating system and architecture.
- proof-of-reserves-linux-amd64-v1.0.2.zip
- proof-of-reserves-linux-arm64-v1.0.2.zip
- proof-of-reserves-macos-v1.0.2.zip
- proof-of-reserves-windows-v1.0.2.zip
2. Unzip the file to a specified directory, for example:
   `~/Downloads/proof-of-reserves-*`
3. Download the file merkel_tree_bg.json and substitute the file with the same name under your directory`~/Downloads/proof-of-reserves-*`
4. Run start file `sh start.sh` or Click the `start.bat` file
5. View results  
   1）If your data are correct and the verification passed, then the result is "Consistent with the Merkle tree root hash. The verification succeeds".
   <img src="images/success.png" alt="" style="text-align:right;width:500px;"/>  
   2）If your data are wrong and the verification fails, the result is "Inconsistent with the Merkle tree root hash. The verification fails".
   <img src="images/faild.png" alt="" style="text-align:right;width:500px;"/>
6. You can also refer to the Bitget Limited open source verification tool code and Merkle tree definition (refer to the "What is the Merkle Tree" section) and write your own program to verify the...

## Destek / Sponsorluk

Bu proje açık kaynak ve topluluk desteği ile sürdürülüyor. Eğer projenin devam etmesini desteklemek veya kurumsal entegrasyon/destek almak isterseniz bizi sponsor olarak destekleyebilir veya ücretli destek paketi satın alabilirsiniz: https://github.com/sponsors/ismailpinrin0-max

[![Sponsor](https://img.shields.io/badge/sponsor-%E2%9D%A4-red)](https://github.com/sponsors/ismailpinrin0-max)

- Bireysel sponsor: https://github.com/sponsors/ismailpinrin0-max
- Topluluk destek (Open Collective): https://opencollective.com/your-project
- Hızlı bağış (PayPal): https://paypal.me/your-link

Ayrıca ücretli kurulum, entegrasyon ve denetim hizmetleri için PAID_SUPPORT.md dosyasına bakın.
