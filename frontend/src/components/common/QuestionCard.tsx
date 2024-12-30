import { cn } from "@/lib/utils";
import { TInputProps } from "@/types/inputProps";
import { Minus, Plus, Trash2 } from "lucide-react";
import { ComponentProps, forwardRef } from "react";
import { Button } from "./Button";
import { Checkbox } from "./checkbox";
import { Header } from "./Header";
import Input from "./Input";
import Label from "./Label";

type TQuestionCardProps = {
    type: "select" | "text";
} & Pick<ComponentProps<"div">, "children">;

const QuestionCard = ({ type, children }: TQuestionCardProps) => {
    return (
        <div className="bg-secondary text-secondary-foreground rounded-[--radius] border border-border p-2 flex flex-col gap-4">
            <Header className="p-0">
                <h4>{type === "select" ? "Select" : "Text"}</h4>
                <Button
                    size={"icon"}
                    variant="destructiveOutline"
                    className="size-8"
                >
                    <Minus />
                </Button>
            </Header>
            <div className="space-y-1">
                <Input
                    type="text"
                    id="questionLabel"
                    placeholder="Question"
                    label="Question"
                />
                <Label
                    htmlFor="questionRequired"
                    className="flex items-center gap-1"
                >
                    <Checkbox
                        id="questionRequired"
                        className="border-input bg-background"
                    />
                    <span>Required</span>
                </Label>
            </div>
            {type === "select" && (
                <div className="space-y-2">
                    <Header className="p-0">
                        <h4>Answers</h4>
                        <Button
                            size={"icon"}
                            variant="outline"
                            className="size-8 border border-accent-foreground bg-transparent hover:bg-background"
                        >
                            <Plus />
                        </Button>
                    </Header>
                </div>
            )}
            {type === "select" && children}
        </div>
    );
};

const AnswerInputField = forwardRef<HTMLInputElement, TInputProps>(
    ({ type, id, placeholder, label, className, ...props }, ref) => {
        return (
            <div className={cn("space-y-1", className)} {...props} key={id}>
                <div className="flex gap-2 items-end">
                    <Input
                        className="flex-grow"
                        ref={ref}
                        type={type ?? "text"}
                        id={"answerLabel-" + id}
                        placeholder={placeholder ?? "Answer"}
                        label={label ?? "Label"}
                    />
                    <Button
                        size="icon"
                        variant="destructiveOutline"
                        className="size-10 aspect-square"
                        aria-description="Remove anwer"
                        title="Remove answer"
                    >
                        <Trash2 />
                    </Button>
                </div>
                <Label
                    htmlFor={"isAnswerCorrect-" + id}
                    className="flex items-center gap-1"
                >
                    <Checkbox
                        id={"isAnswerCorrect-" + id}
                        className="border-input bg-background"
                    />
                    <span>Correct Answer</span>
                </Label>
            </div>
        );
    }
);

export { AnswerInputField, QuestionCard };
