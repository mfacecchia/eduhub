import { getAccountInitialsFullName } from "@/lib/accountInitials";
import { ArrowUpRight } from "lucide-react";
import { Link } from "react-router";
import { Avatar, AvatarFallback, AvatarImage } from "./common/Avatar";
import { Card, CardFooter, CardHeader, CardTitle } from "./common/Card";

type TClassCardProps = {
    courseName: string;
    teacherName: string;
    teacherAvatarUrl?: string;
    linkToClass: string;
};

const ClassCard = ({
    courseName,
    teacherName,
    teacherAvatarUrl,
    linkToClass,
}: TClassCardProps) => {
    return (
        <Link to={linkToClass}>
            <Card className="bg-gradient-to-tr from-[#D4145A] to-[#FBB03B] text-primary-foreground aspect-[3/4] flex flex-col justify-between">
                <CardHeader>
                    <CardTitle className="flex justify-between">
                        <span className="break-words max-w-[80%]">
                            {courseName}
                        </span>
                        <ArrowUpRight className="size-5" />
                    </CardTitle>
                </CardHeader>
                <CardFooter>
                    <div className="bg-secondary rounded-full flex items-center gap-1 p-1 text-secondary-foreground">
                        <Avatar className="size-6">
                            <AvatarFallback className="bg-muted-foreground text-muted text-xs">
                                {getAccountInitialsFullName(teacherName)}
                            </AvatarFallback>
                            {teacherAvatarUrl && (
                                <AvatarImage src={teacherAvatarUrl} />
                            )}
                        </Avatar>
                        <span className="small">{teacherName}</span>
                    </div>
                </CardFooter>
            </Card>
        </Link>
    );
};

export default ClassCard;
