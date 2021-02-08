# tf-idf-search
TF-IDF is a statistical measure that evaluates how relevant a word is to a document in a collection of documents. This is done by multiplying two metrics: how many times a word appears in a document, and the inverse document frequency of the word across a set of documents.  

It has many uses, most importantly in automated text analysis, and is very useful for scoring words in machine learning algorithms for Natural Language Processing (NLP).  

My TF-IDF search implementation using Java, Zookeeper, and Spring integration for managing the search cluster. The cluster can be scale when adding more workers replicas, to make the search operation faster.  The worker/leader is elected when the application starts to run, and when the leader dies, a new leader is elected immediately. 
Services:  
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

## Run with Kubernetes 
kubectl apply -f Kubernetes/  

## Usage 
Rest get request:  
host-name:listening-port/search?term=term_to_search

