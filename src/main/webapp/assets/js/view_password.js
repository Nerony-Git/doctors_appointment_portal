

// Wait for the DOM to be fully loaded
document.addEventListener('DOMContentLoaded', () => {
// Show Password Script
    const togglePassword = document.querySelector('#togglePassword');
    const password = document.querySelector('#password');
    togglePassword.addEventListener('click', function () {
        // Toggle the type attribute using getAttribute() method
        const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
        password.setAttribute('type', type);
        // Toggle the eye and bi-eye icon
        this.classList.toggle('bi-eye');
    });
});
