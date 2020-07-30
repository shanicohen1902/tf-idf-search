# tf-idf-search
Distributed TF-IDF search using Java, Zookeeper, and Spring integration. The data stores in text files (resources/files). 
* front-end service: looking for the leader and send him the task
* tf-idf-distributed: leader/worker service.   
If the instance is a leader, it will send a chunk from the tasks to each worker, and then will aggregate the results. 
The workers should calculate the TF for each document

## TF_IDF:
TF = (Number of times term t appears in a document) / (Total number of terms in the document). (worker job).  
IDF = log_e(Total number of documents / Number of documents with term t in it). (leader job). 
TF_IDF weight is the product of these quantities: TF * IDF 


## Run with docker-compose
docker-compose up 
## scale
docker-compose scale microservice=2 (or 3...)

## Usage 
Rest get request:  
host-name:8087/search?term=term_to_search

