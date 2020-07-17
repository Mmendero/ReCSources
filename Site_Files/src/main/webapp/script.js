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
