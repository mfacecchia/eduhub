import { ArrowUpRight, CalendarDays, Clock4, Hash } from "lucide-react";
import { Link } from "react-router";
import { Card, CardContent, CardHeader, CardTitle } from "./common/card";

type TLessonCardProps = {
    courseName: string;
    lessonDate: string;
    startsAt: string;
    endsAt: string;
    roomNo: string;
    linkToLesson: string;
};

const LessonCard = ({
    courseName,
    lessonDate,
    startsAt,
    endsAt,
    roomNo,
    linkToLesson,
}: TLessonCardProps) => {
    return (
        <Link to={linkToLesson}>
            <Card>
                <CardHeader>
                    <CardTitle className="flex justify-between">
                        {courseName}
                        <ArrowUpRight className="size-5" />
                    </CardTitle>
                </CardHeader>
                <CardContent>
                    <div className="flex items-center gap-1">
                        <CalendarDays className="text-muted-foreground size-4" />
                        <span>{lessonDate}</span>
                    </div>
                    <div className="flex items-center gap-1">
                        <Clock4 className="text-muted-foreground size-4" />
                        <span>
                            {startsAt} - {endsAt}
                        </span>
                    </div>
                    <div className="flex items-center gap-1">
                        <Hash className="text-muted-foreground size-4" />
                        <span>Room {roomNo}</span>
                    </div>
                </CardContent>
            </Card>
        </Link>
    );
};

export default LessonCard;
