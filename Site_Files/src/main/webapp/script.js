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
  liElement.innerText ="Email: "+text.email+"  Name: "+text.name+"  Type： "+ text.type+"  Comment: "+text.comment;
  return liElement;
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

/**
 * Retrieves the posts from the /inf servlet and injects it into the page
 */
function getAllPosts() {
  fetch('/inf').then(response => response.json()).then((posts) => {
    const postsListElement = document.getElementById('posts-container');

    // for each message, display it in a list
    posts.forEach((post) => {
        postsListElement.appendChild(createPostElement(post));
        loadStarRatings(post.id, post.rating);
        storeStarRatings(post.id);
    });
  });
}

/**
 * Create each post element using the DataStore information
 */
function createPostElement(postData) {

  const divElement = document.createElement('div');
  divElement.className = "card";
  divElement.id = "post-" + postData.id;
  var postColor = "white";
  if(postData.type == "book"){
      postColor = '#6699cc';
  }
  else if(postData.type == "podcasts"){
      postColor = '#9370db';
  }
  else if(postData.type == "internship"){
      postColor = '#be0032';
  }
  divElement.style.backgroundColor = postColor;

  const nameElement = document.createElement('h3');
  nameElement.innerText = postData.name;

  const typeElement = document.createElement('span');
  typeElement.innerText = postData.type;
  typeElement.className = "category";
  typeElement.style.color = postColor;

  const dateElement = document.createElement('span');
  const date = new Date(postData.timestamp);
  const dateTime = " - " + date.toDateString() + " " + date.getHours() + ":" + date.getMinutes();
  dateElement.innerText = dateTime;

  const commentElement = document.createElement('p');
  commentElement.innerText = postData.comment;
  commentElement.className = "comment";
  
  const starRatingElement = document.createElement('input');
  starRatingElement.className = 'rating-loading';
  starRatingElement.type = 'text';
  starRatingElement.id = 'rating-' + postData.id;
  starRatingElement.value = postData.rating;
  
  divElement.appendChild(nameElement);
  divElement.appendChild(typeElement);
  divElement.appendChild(dateElement);
  divElement.appendChild(commentElement);
  divElement.appendChild(starRatingElement);
  
  return divElement;
}

/**
 * Loads the star rating settings into the page.
 */
function loadStarRatings(id, rating){
    $(document).ready(function(){
        $("#rating-" + id).rating({
        step: 1,
        starCaptions: {1: 'Very Poor', 2: 'Poor', 3: 'Ok', 4: 'Good', 5: 'Very Good'},
        starCaptionClasses: {1: 'text-danger', 2: 'text-warning', 3: 'text-info', 4: 'text-primary', 5: 'text-success'},
        disabled: rating > 0,
        showClear: rating == 0 
        });
    });
}

/**
 * Stores the star rating to the DataStore.
 */
function storeStarRatings(id){
    $(document).ready(function(){
        $("#rating-" + id).on('rating:change', function(event, value, caption) {
            
            changeRating = {
                rating: value,
                id: id
            };
            
            $.post("change-rating", changeRating, function(data, status, xhr) {
                if(status == "success"){
                    // Disables the user from submitting another review
                    $("#rating-" + id).rating("refresh", {disabled:true, showClear:false});
                    console.log("Rating success!");
                }
            });
        });
    });   
}
