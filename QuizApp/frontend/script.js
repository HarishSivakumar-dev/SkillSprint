function sendBackendRequest()
{

    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    console.log('Username: ' + username);
    console.log('Password: ' + password);

    const data ={
        "userName": username,
        "password": password
    }

    fetch('https://localhost/app/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' ,
                    'X-Username' : username
        },
        body: JSON.stringify(data)
    }).then(response => response.text())
    .then(data => {
        alert('Login successful: ' + data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}