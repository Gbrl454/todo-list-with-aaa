import { useContext, useRef, useState } from "react";
import { TasksContext } from "../../context/TasksContext";
import { DialogClose, DialogPortal, DialogTitle } from "@radix-ui/react-dialog";
import { Content, CreateButton, Overlay } from "./styles";

interface CreateTaskProps {
    onSuccess: () => void;
}

export function CreateTask({ onSuccess }: CreateTaskProps) {
    const { CreateTask } = useContext(TasksContext);
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [visibilite, setVisibilite] = useState("public");
    const [limitDate, setLimitDate] = useState("");
    const closeRef = useRef<HTMLButtonElement>(null);
    async function handleSubmit(e: React.FormEvent) {
        e.preventDefault();
        try {
            await CreateTask({
                nmTask: name,
                dsTask: description,
                isPrivateTask: visibilite === "private",
                dtDeadline: limitDate,
            });
            onSuccess();
            closeRef.current?.click();
            setName('');
            setDescription('')
            setVisibilite('')
            setLimitDate('')
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <DialogPortal>
            <Overlay />
            <Content>
                <DialogTitle>Registrar Task</DialogTitle>

                <form
                    onSubmit={handleSubmit}
                    style={{ display: "flex", flexDirection: "column", gap: "1rem" }}
                >
                    <input
                        type="text"
                        placeholder="Nome da tarefa"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                    <textarea
                        placeholder="Descrição"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                    <select value={visibilite} onChange={(e) => setVisibilite(e.target.value)}>
                        <option value="public">Pública</option>
                        <option value="private">Privada</option>
                    </select>
                    <input
                        type="date"
                        value={limitDate}
                        onChange={(e) => setLimitDate(e.target.value)}
                        required
                    />
                    <CreateButton type="submit" className="CreateTaskButton">
                        Criar Tarefa
                    </CreateButton>
                </form>
                <DialogClose ref={closeRef} className="closeButton"  />
            </Content>
        </DialogPortal>
    );
}
