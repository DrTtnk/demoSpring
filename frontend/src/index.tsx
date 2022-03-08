import React from 'react';
import ReactDOM from 'react-dom';
import * as M from "@mui/material"; // <-- example of importing a whole module
import axios from "axios";

type Note = { title: string, content: string };

const getNotes = async () => {
    const result = await axios.get('http://localhost:8080/notes')
    return result.data as Note[];
}

const saveNote = async (note: Note) => {
    await axios.post('http://localhost:8080/notes/create', note)
}

type NoteProps = {
    title: string;
    content: string;
}

type User  = {
    name: string;
    email: string;
    password: string;
}

const Note = (prop: NoteProps) => {
    return <div>
        <h4>{prop.title}</h4>
        <p>{prop.content}</p>
    </div>
};

const Notes = () => {
    const [newTitle, setNewTitle] = React.useState("");
    const [newContent, setNewContent] = React.useState("");
    const [notes, setNotes] = React.useState<NoteProps[]>([]);

    React.useEffect(() => { getNotes().then(res => setNotes(res)); }, []);

    const handleAddNote = async () => {
        if(!newTitle || !newContent)
            return;
        const note = { title: newTitle, content: newContent };
        await saveNote(note);
        setNotes([...notes, note]);
        setNewTitle("");
        setNewContent("");
    }

    const handleInputTitle = (e: any) => setNewTitle(e.target.value);
    const handleInputContent = (e: any) => setNewContent(e.target.value);

    return <div>
        <M.TextField placeholder="title" value={newTitle} variant="outlined" onInput={handleInputTitle}></M.TextField >
        <M.TextField placeholder="content" value={newContent} variant="outlined" onInput={handleInputContent}></M.TextField >
        <M.Button variant="contained" onClick={handleAddNote}>Add</M.Button>

        {notes.map((n, id) => <Note key={id} title={n.title} content={n.content}></Note>)}
    </div>
};

const Users = () => <div>
    <br/>ToDo this part, like we did for the notes, but for the Users, nothing too fancy,
    <br/>just a coy and paste from what we did, just try it at least once to bhe sure that it works... <h4>have fun! :D</h4>
    <br/>For more info if you want to play <a href="https://mui.com/components/autocomplete/">with mui</a>
</div>

const App = () => {
    return <M.Grid container>
        <M.Grid item xs><Notes/></M.Grid>
        <M.Divider orientation="vertical" flexItem/>
        <M.Grid item xs><Users/></M.Grid>
    </M.Grid>
}

ReactDOM.render(
    <React.StrictMode>
        <App/>
    </React.StrictMode>,
    document.getElementById('root')
);
