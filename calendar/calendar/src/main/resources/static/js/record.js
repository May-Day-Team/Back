// Placeholder for journals
let journals = [];

// Function to save or update a journal entry
function saveOrUpdateJournal() {
    const journalTitle = document.getElementById('journalTitle').value;
    const journalDate = document.getElementById('journalDate').value;
    const journalContent = document.getElementById('journalContent').value;
    if (journalTitle.trim() && journalDate && journalContent.trim()) {
        const currentIndex = document.getElementById('journalForm').dataset.currentIndex;
        if (currentIndex !== undefined && currentIndex !== '') {
            // Update existing journal
            journals[currentIndex] = { title: journalTitle, date: journalDate, content: journalContent };
        } else {
            // Save new journal
            journals.push({ title: journalTitle, date: journalDate, content: journalContent });
        }
        updateJournalList();
        clearForm();
    }
}

// Function to delete a journal entry
function deleteJournal() {
    const currentIndex = document.getElementById('journalForm').dataset.currentIndex;
    if (currentIndex !== undefined && currentIndex !== '') {
        journals.splice(currentIndex, 1);
        updateJournalList();
        clearForm();
    }
}

// Function to update the journal list
function updateJournalList() {
    const entries = document.getElementById('entries');
    entries.innerHTML = '';
    journals.forEach((entry, index) => {
        const listItem = document.createElement('li');
        listItem.textContent = `${entry.title} (${entry.date})`;
        listItem.onclick = () => loadJournal(index);
        entries.appendChild(listItem);
    });
}

// Function to load a journal entry into the editor
function loadJournal(index) {
    document.getElementById('journalTitle').value = journals[index].title;
    document.getElementById('journalDate').value = journals[index].date;
    document.getElementById('journalContent').value = journals[index].content;
    document.getElementById('journalForm').dataset.currentIndex = index;
}

// Function to clear the form
function clearForm() {
    document.getElementById('journalTitle').value = '';
    document.getElementById('journalDate').value = new Date().toISOString().split('T')[0];
    document.getElementById('journalContent').value = '';
    document.getElementById('journalForm').dataset.currentIndex = '';
}

// Set today's date on load
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('journalDate').value = new Date().toISOString().split('T')[0];
});
