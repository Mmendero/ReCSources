// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}


function getHistory() {
  fetch('/inf').then(response => response.json()).then((comment) => {
   console.log(comment);
    const historyEl = document.getElementById('history');
    comment.forEach((line) => {
      historyEl.appendChild(createListElement(line));
    });
  });
}


function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText ="Email: "+text.email+" Nick name: "+text.name+" Comment: "+text.comment;
  return liElement;
}

/**
 * Retrieves the posts from the /getInf servlet and injects it into the page
 */
function getAllPosts() {
  fetch('/getInf').then(response => response.json()).then((posts) => {
      
    const postsListElement = document.getElementById('posts-container');
    postsListElement.innerHTML = '';

    // for each message, display it in a list
    posts.forEach(post => {
        postsListElement.appendChild(createPostElement(post));
        // Sets up the functionality for the ratings
        loadStarRatings(post.id);
        storeStarRatings(post.id);
    });
  });
}

/**
 * Create each post element using the DataStore information
 */
function createPostElement(postData) {
  const liElement = document.createElement('li');
  liElement.className = 'post' + postData.id;

  const nameElement = document.createElement('span');
  nameElement.innerText = postData.name + " (" + postData.email + ")" ;

  const typeElement = document.createElement('span');
  typeElement.innerText = postData.type;

  const dateElement = document.createElement('span');
  const date = new Date(postData.timestamp);
  const dateTime = " - " + date.toDateString() + " " + date.getHours() + ":" + date.getMinutes();
  dateElement.innerText = dateTime;

  const commentElement = document.createElement('p');
  commentElement.innerText = postData.comment;

  const starRatingElement = document.createElement('input');
  starRatingElement.className = 'rating-loading';
  starRatingElement.type = 'text';
  starRatingElement.id = 'stars' + postData.id;
  //TODO: Add field in the data class to store rating values 
  if(postData.value != 0){
    starRatingElement.value = postData.value;
  }
  
  liElement.appendChild(nameElement);
  liElement.appendChild(textElement);
  liElement.appendChild(dateElement);
  liElement.appendChild(commentElement);
  liElement.appendChild(starRatingElement);
  
  return liElement;
}

/**
 * Loads the star rating settings into the page.
 */
function loadStarRatings(id){
    $(document).ready(function(){
        $(id).rating({
        step: 1,
        starCaptions: {1: 'Very Poor', 2: 'Poor', 3: 'Ok', 4: 'Good', 5: 'Very Good'},
        starCaptionClasses: {1: 'text-danger', 2: 'text-warning', 3: 'text-info', 4: 'text-primary', 5: 'text-success'}
        });
    });

}
function search(){
   let name= document.getElementById("name-search");
  

    let type= document.getElementById("type-search");
  
   
   fetch('/getInf?name-search='+name.value + '&type-search=' +type.value).then(response => response.json()).then((comment) => {
   console.log(comment);
   const historyEl = document.getElementById('history');
    comment.forEach((line) => {
      historyEl.appendChild(createListElement(line));
    });
  });
}

function storeStarRatings(id){
    $(document).ready(function(){
        console.log($(id).find("filled-stars"));
        $(id).on('rating:change', function(event, value, caption) {
            //TODO: Connect to Database to store star values
            console.log(value);
            console.log(event);
            console.log(caption);

            // Disables the user from submitting another review
            $(id).rating("refresh", {disabled:true, showClear:false});
        });
    });
    
}
