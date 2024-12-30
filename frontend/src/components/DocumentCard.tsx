import { ArrowUpRight, CalendarDays, Download, File, Info } from "lucide-react";
import { Link } from "react-router";
import { Card, CardContent, CardHeader, CardTitle } from "./common/Card";

type TDocumentCardProps = {
    fileName: string;
    docType: string;
    size: string;
    sharedDate: string;
    link: string;
};

const DocumentCard = ({
    fileName,
    docType,
    size,
    sharedDate,
    link,
}: TDocumentCardProps) => {
    // TODO: Add a popup animation on hover
    return (
        <Link to={link}>
            <Card>
                <CardHeader>
                    <CardTitle className="flex justify-between">
                        <div className="flex items-center gap-1 max-w-[80%] text-wrap">
                            {fileName}
                        </div>
                        {docType.toLowerCase() === "link" ? (
                            <ArrowUpRight className="size-5" />
                        ) : (
                            <Download className="size-5" />
                        )}
                    </CardTitle>
                </CardHeader>
                <CardContent>
                    <div className="flex items-center gap-1">
                        <File className="text-muted-foreground size-4" />
                        <span>{docType} document</span>
                    </div>
                    <div className="flex items-center gap-1">
                        <Info className="text-muted-foreground size-4" />
                        <span>Size: {size}</span>
                    </div>
                    <div className="flex items-center gap-1">
                        <CalendarDays className="text-muted-foreground size-4" />
                        <span>{sharedDate}</span>
                    </div>
                </CardContent>
            </Card>
        </Link>
    );
};

export default DocumentCard;
