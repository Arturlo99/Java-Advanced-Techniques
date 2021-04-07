# Java-Advanced-Techniques
Repository contains solved tasks for university course called "Programming in Java language".

## Table of Contents
- [Lab01 - File Comparator](#lab01)
- [Lab02 - File Viewer](#lab02)
- [Lab03 - Geo Quiz](#lab03)

## Lab01 - File Comparator <a name = "lab01"></a>
Desktop application which allows you to compare actual files in the given directory to files since last done snapshot.  
Comparing works with usage of MD5 Hash.

| ![Screenshot_8](https://user-images.githubusercontent.com/49612999/113924781-7f5ee900-97ea-11eb-9e69-0ca75006a36b.png)|  
|:--:| 
| *Image1: File Comparator usage* |

## Lab02 - File Viewer<a name = "lab02"></a>
Application displays list of all .txt and .png files within chosen directory.
You can load file content by clicking it's path in ListView.  
Loading files content is done by using WeakReference.

|![Screenshot_1](https://user-images.githubusercontent.com/49612999/113924704-6a825580-97ea-11eb-837b-15812933f764.png)|
|:--:| 
| *Image2: File Viewer displaying .png file* |

|![Screenshot_2](https://user-images.githubusercontent.com/49612999/113926396-635c4700-97ec-11eb-844d-f11a943bcf89.png)|
|:--:| 
| *Image3: File Viewer displaying .txt file content* |

## Lab03 - Geo Quiz<a name = "lab03"></a>
Geo Quiz is a Spring Boot application providing geographic quiz functionality.
You can parametrize questions and answer to them. When you wrote your answer you can check if it was correct.
Appliation sends http request to REST API and displays correct answer.
Application uses Language Bundle. It provides English and Polish language versions.

|![Screenshot_2](https://user-images.githubusercontent.com/49612999/113926608-ac140000-97ec-11eb-8005-bff2312bbfde.png)|
|:--:| 
| *Image4: Geo Quiz - Polish*|

|![Screenshot_3](https://user-images.githubusercontent.com/49612999/113926762-e382ac80-97ec-11eb-93f3-3bb9fd585516.png)|
|:--:| 
| *Image5: Geo Quiz - English*|
