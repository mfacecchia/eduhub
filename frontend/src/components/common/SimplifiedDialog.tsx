import { ComponentProps, ElementRef, forwardRef, ReactNode } from "react";
import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
    AlertDialogTrigger,
} from "./AlertDialog";

type TDialogProps = {
    dialogTrigger: ReactNode;
    title: ReactNode;
    description?: ReactNode;
    cancelButton: ReactNode;
    confirmButton: ReactNode;
} & ComponentProps<typeof AlertDialogContent>;

const SimplifiedDialog = forwardRef<
    ElementRef<typeof AlertDialogContent>,
    TDialogProps
>(
    (
        {
            dialogTrigger,
            title,
            description,
            cancelButton,
            confirmButton,
            ...props
        },
        ref
    ) => {
        return (
            <AlertDialog>
                <AlertDialogTrigger asChild>{dialogTrigger}</AlertDialogTrigger>
                <AlertDialogContent {...props} ref={ref}>
                    <AlertDialogHeader>
                        <AlertDialogTitle>{title}</AlertDialogTitle>
                        <AlertDialogDescription>
                            {description}
                        </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                        <AlertDialogCancel>{cancelButton}</AlertDialogCancel>
                        <AlertDialogAction>{confirmButton}</AlertDialogAction>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        );
    }
);

export default SimplifiedDialog;
