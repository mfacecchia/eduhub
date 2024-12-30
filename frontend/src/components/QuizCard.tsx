import { ArrowUpRight, CalendarClock, CalendarDays } from "lucide-react";
import { Link } from "react-router";
import { Card, CardContent, CardHeader, CardTitle } from "./common/Card";

type TLessonCardProps = {
    title: string;
    assignedDate: string;
    dueDate: string;
    linkToQuiz: string;
};

const LessonCard = ({
    title,
    assignedDate,
    dueDate,
    linkToQuiz,
}: TLessonCardProps) => {
    // TODO: Add a popup animation on hover
    return (
        <Link to={linkToQuiz}>
            <Card>
                <CardHeader>
                    <CardTitle className="flex justify-between">
                        {title}
                        <ArrowUpRight className="size-5" />
                    </CardTitle>
                </CardHeader>
                <CardContent>
                    <div className="flex items-center gap-1">
                        <CalendarDays className="text-muted-foreground size-4" />
                        <span>{assignedDate}</span>
                    </div>
                    <div className="flex items-center gap-1">
                        <CalendarClock className="text-muted-foreground size-4" />
                        <span>{dueDate}</span>
                    </div>
                </CardContent>
            </Card>
        </Link>
    );
};

export default LessonCard;
